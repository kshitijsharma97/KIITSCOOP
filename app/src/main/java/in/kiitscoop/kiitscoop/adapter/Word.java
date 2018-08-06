
package in.kiitscoop.kiitscoop.adapter;


import android.graphics.Bitmap;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains resource IDs for the default translation, Miwok translation, audio file, and
 * optional image file for that word.
 */
public class Word {

    /** String resource ID for the default translation of the word */
    private String mDefaultdeptnameId;

    /** String resource ID for the Miwok translation of the word */
    private String mDepartmentId;


    /** Image resource ID for the word */
    private String mImage1ResourceId ;
    /** Image resource ID for the word */
    private String mImage2ResourceId ;
        private String mNewsResourceId;
        private String mNewsUrl;



    public Word(String defaultTranslationId, String deptId, String image1ResourceId, String image2ResourceId, String newsID,String newsURL) {
        mDefaultdeptnameId = defaultTranslationId;
        mDepartmentId = deptId;
        mImage1ResourceId = image1ResourceId;
        mImage2ResourceId = image2ResourceId;
        mNewsResourceId=newsID;
        mNewsUrl = newsURL;
    }



    /**
     * Get the string resource ID for the default translation of the word.
     */
    public String getDefaultdeptnameId() {
        return mDefaultdeptnameId;
    }

    /**
     * Get the string resource ID for the Miwok translation of the word.
     */
    public String getmDepartmentId() {
        return mDepartmentId;
    }

    /**
     * Return the image resource ID of the word.
     */
    public String getImage1ResourceId() {
        return mImage1ResourceId;
    }


    public String getImage2ResourceId() {
        return mImage2ResourceId;
    }

    public String getmNewsResourceId(){
        return mNewsResourceId;
    }

    public String getmNewsUrl() {
        return mNewsUrl;
    }
}