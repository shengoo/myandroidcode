package com.sheng00.test.unziptest;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import de.idyl.crypto.zip.AesZipFileDecrypter;
import de.idyl.crypto.zip.impl.ExtZipEntry;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        AesZipFileDecrypter zipFile;
		try {
//			zipFile = new AesZipFileDecrypter( new File(Environment.getExternalStorageDirectory() + File.separator +"1winzipEncryptedFile.zip") );
			zipFile = new AesZipFileDecrypter( new File(Environment.getExternalStorageDirectory() + File.separator +"epub/alicezip.zip") );
	        ExtZipEntry entry = zipFile.getEntry( "META-INF/container.xml" );
	        File outFile = new File(Environment.getExternalStorageDirectory() + File.separator + "META-INF/container.xml");
	        if (!outFile.getParentFile().exists()) {
	        	System.out.println("file parent not exist");
				boolean creatfile = outFile.getParentFile().mkdirs();
				System.out.println("create folder " + (creatfile?"success":"fail"));
			}else {
				System.out.println("file parent exists");
			}
	        zipFile.extractEntry( entry, outFile, "123456" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		}     
		System.out.println("success");
    }
}