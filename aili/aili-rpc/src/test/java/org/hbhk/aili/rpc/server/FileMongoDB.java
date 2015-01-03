package org.hbhk.aili.nosql.server;

import java.io.File;
import java.net.UnknownHostException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class FileMongoDB {
	Mongo mongo = null;
	private DBCollection collection;
	DB db;

	@Before
	public void before() throws UnknownHostException {
		mongo = new Mongo();
		db = mongo.getDB("hbhk");
		Set<String> collections = db.getCollectionNames();
		for (String collectionName : collections) {
			System.out.println(collectionName);
		}
		collection = db.getCollection("hbhk-c");
	}

	@Test
	public void saveFile() {

		try {
			String newFileName = "mkyong-java-image";
			File imageFile = new File("src/test/resources/deptget.jpg");
			GridFS gfsPhoto = new GridFS(db, "photo");
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
			gfsFile.setFilename(newFileName);
			gfsFile.save();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void readFile() {

		try {
			String newFileName = "mkyong-java-image";
			GridFS gfsPhoto = new GridFS(db, "photo");
			GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
			System.out.println(imageForOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void queryAllFile() {
		GridFS gfsPhoto = new GridFS(db, "photo");
		DBCursor cursor = gfsPhoto.getFileList();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	@Test
	public void readFileToSave() {
		try {
			String newFileName = "mkyong-java-image";
			GridFS gfsPhoto = new GridFS(db, "photo");
			GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
			imageForOutput.writeTo("c:\\hbhk.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void delFile() {
		String newFileName = "mkyong-java-image";
		GridFS gfsPhoto = new GridFS(db, "photo");
		gfsPhoto.remove(gfsPhoto.findOne(newFileName));
	}
}
