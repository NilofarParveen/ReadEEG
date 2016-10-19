package idlework.eegreader.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager extends AppCompatActivity {
	// SDCard Path
	//final String MEDIA_PATH = new String(Environment.getExternalStorageDirectory().getAbsolutePath().toString()); //edited
    final String MEDIA_PATH = new String("/storage/extSdCard/songs/"); //edited
    public String TAG = "hello";
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG,"Permission is granted");
                return true;
            } else {

                Log.d(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.d(TAG,"Permission is granted");
            return true;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
	//public String  MEDIA_PATH = new String();
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	// Constructor
	public SongsManager()
	{

	}
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList()
	{
       File home = new File(MEDIA_PATH); // edited
      //  File home = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) ;

        if (home.listFiles(new FileExtensionFilter()).length > 0)
       {//edited
        for (File file : home.listFiles(new FileExtensionFilter())) {
				HashMap<String, String> song = new HashMap<String, String>();
				song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
				song.put("songPath", file.getPath());
				// Adding each song to SongList
				songsList.add(song);
			}
		}
		// return songs list array
		return songsList;
	}
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
}
