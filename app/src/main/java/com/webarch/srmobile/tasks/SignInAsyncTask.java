package com.webarch.srmobile.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.Activity;
import com.webarch.srmobile.activities.main.MainActivity;
import com.webarch.srmobile.activities.sub.evarsity.EvarsityActivity;
import com.webarch.srmobile.activities.sub.views.ViewPager;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.parsers.EvarsityParser;
import com.webarch.srmobile.parsers.ParserException;
import com.webarch.srmobile.parsers.ProfileParser;
import com.webarch.srmobile.parsers.SignInParser;
import com.webarch.srmobile.sqlitehelpers.EvarsitySqliteHelper;
import com.webarch.srmobile.sqlitehelpers.UsersSqliteHelper;
import com.webarch.srmobile.utils.HttpUtils;
import com.webarch.srmobile.views.quickaccess.UserButton;
import com.webarch.srmobile.views.signin.SignInDialogFragment;
import com.webarch.srmobile.views.signin.SigningInDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

//import com.webarch.srmobile.parsers.srmobile.UserParser;

/**
 * @author Manoj Khanna
 */

public class SignInAsyncTask extends AsyncTask<String, Void, Integer> {

    private static final int RESULT_CONNECTION_ERROR = 0;
    private static final int RESULT_SIGN_IN_ERROR = 1;
    private static final int RESULT_SUCCESS = 2;
    private Activity activity;
    private SigningInDialogFragment signingInDialogFragment;
    private String cookie;

    public SignInAsyncTask(Activity activity) {
        this.activity = activity;
        activity.setSignInAsyncTask(this);
    }

    @Override
    protected Integer doInBackground(String... params) {
        if (params[0].isEmpty() || params[1].isEmpty()) {
            return RESULT_SIGN_IN_ERROR;
        }

        try {
            SignInParser signInParser = new SignInParser(params[0], params[1]);
            try {
                signInParser.execute();
            } catch (ParserException ignored) {
            }

            if (isCancelled()) {
                return null;
            }

            if (signInParser.isError()) {
                return RESULT_SIGN_IN_ERROR;
            }

            cookie = signInParser.getCookie();

            UsersSqliteHelper usersSqliteHelper = new UsersSqliteHelper(activity);
            String[] credentials = usersSqliteHelper.readCredentials();
            usersSqliteHelper.close();
            if (credentials == null || !credentials[0].equals(params[0])) {
//                UserParser userParser = new UserParser(params[0]);
//                userParser.executeIgnoreException();

                if (isCancelled()) {
                    return null;
                }

                String regNo, name, fathersName = null, dateOfBirth, sex = null, bloodGroup = null, office, course, semester, section, address = null, pincode = null, email = null, validity = null;
                Bitmap originalPictureBitmap;
//                if (userMatchListMap == null || userMatchListMap.isEmpty()) {
                EvarsityParser evarsityParser = new EvarsityParser(cookie);
                evarsityParser.execute();

                if (isCancelled()) {
                    return null;
                }

                semester = evarsityParser.getSemester();
                section = evarsityParser.getSection();

                ProfileParser profileParser = new ProfileParser(cookie);
                profileParser.execute();

                if (isCancelled()) {
                    return null;
                }

                regNo = profileParser.getRegNo();
                name = profileParser.getName();
                fathersName = profileParser.getFathersName();

                originalPictureBitmap = BitmapFactory.decodeStream(HttpUtils.sendHttpRequest(profileParser.getPictureUrlString(), cookie).getInputStream());
                originalPictureBitmap = Bitmap.createScaledBitmap(originalPictureBitmap, 225, originalPictureBitmap.getHeight() * 225 / originalPictureBitmap.getWidth(), false);

                if (isCancelled()) {
                    return null;
                }

                dateOfBirth = profileParser.getDateOfBirth();
                sex = profileParser.getSex();
                bloodGroup = profileParser.getBloodGroup();
                office = profileParser.getOffice();
                course = profileParser.getCourse();
                address = profileParser.getAddress();
                pincode = profileParser.getPincode();
                email = profileParser.getEmail();
                validity = profileParser.getValidity();

//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    originalPictureBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
//                    use byteArrayOutputStream.toByteArray() for picture
//                    HttpUtils.sendPostRequest("my server uploading link", new HashMap<String, String>());//TODO: Upload only user details to my server
//                } else {
//                    ArrayList<String> userResultList = userMatchListMap.get("user").get(0);
//                    regNo = userResultList.get(0);
//                    name = userResultList.get(1);
//                    originalPictureBitmap = BitmapFactory.decodeStream(HttpUtils.sendHttpRequest(userResultList.get(2)).getInputStream());
//                    dateOfBirth = userResultList.get(3);
//                    office = userResultList.get(4);
//                    course = userResultList.get(5);
//                    semester = userResultList.get(6);
//                    section = userResultList.get(7);
//                }

                if (isCancelled()) {
                    return null;
                }

                float density = activity.getResources().getDisplayMetrics().density;

                EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(activity);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int size = (int) (75 * density);
                Bitmap.createScaledBitmap(originalPictureBitmap, size, originalPictureBitmap.getHeight() * size / originalPictureBitmap.getWidth(), false)
                        .compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                evarsitySqliteHelper.writeProfile(regNo, name, fathersName, byteArrayOutputStream.toByteArray(), dateOfBirth, sex, bloodGroup, office, course, semester, section, address, pincode, email, validity);
                byteArrayOutputStream.close();
                evarsitySqliteHelper.close();

                if (isCancelled()) {
                    return null;
                }

                byteArrayOutputStream = new ByteArrayOutputStream();
                size = (int) (50 * density);
                Bitmap.createScaledBitmap(originalPictureBitmap, size, originalPictureBitmap.getHeight() * size / originalPictureBitmap.getWidth(), false)
                        .compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                usersSqliteHelper.writeUser(regNo, name, byteArrayOutputStream.toByteArray(), dateOfBirth, office, course, semester, section);
                byteArrayOutputStream.close();
            }

            usersSqliteHelper = new UsersSqliteHelper(activity);
            usersSqliteHelper.writeCredentials(params[0], params[1]);
            usersSqliteHelper.close();

            return RESULT_SUCCESS;
        } catch (IOException | ParserException ex) {
            Log.e(SignInAsyncTask.class.getName(), ex.toString());

            if (ex instanceof ParserException) {
                //TODO: Run exception handler here
            }

            return RESULT_CONNECTION_ERROR;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        signingInDialogFragment = new SigningInDialogFragment();
        signingInDialogFragment.setSignInAsyncTask(this);
        signingInDialogFragment.show(activity.getSupportFragmentManager(), SigningInDialogFragment.class.getName());
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        signingInDialogFragment.dismiss();

        switch (result) {

            case RESULT_CONNECTION_ERROR:
                new SignInDialogFragment().show(activity.getSupportFragmentManager(), SignInDialogFragment.class.getName());

                Toast.makeText(activity, "Connection unavailable!", Toast.LENGTH_SHORT).show();
                break;

            case RESULT_SIGN_IN_ERROR:
                new SignInDialogFragment().show(activity.getSupportFragmentManager(), SignInDialogFragment.class.getName());

                Toast.makeText(activity, "Incorrect credentials!", Toast.LENGTH_SHORT).show();
                break;

            case RESULT_SUCCESS:
                User.create(activity, cookie);

                ((UserButton) activity.findViewById(R.id.quick_access_pane_user_button)).signIn();

                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).onSignedIn();
                } else if (activity instanceof EvarsityActivity) {
                    ((ViewPager) activity.findViewById(R.id.sub_view_pager)).getAdapter().notifyDataSetChanged();
                }
                break;

        }
    }

}
