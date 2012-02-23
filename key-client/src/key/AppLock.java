package key;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.commons.codec.digest.DigestUtils;

public class AppLock {

	/** hidden constructor */
	private AppLock() {
	}

	/** Lock file */
	File lock_file = null;

	FileLock lock = null;

	FileChannel lock_channel = null;

	FileOutputStream lock_stream = null;

	/**
	 * Creates class lock instance
	 * 
	 * @param key
	 *            Unique application key
	 */
	private AppLock(String key) throws Exception {
		String tmp_dir = System.getProperty("java.io.tmpdir");
		if (!tmp_dir.endsWith(System.getProperty("file.separator"))) {
			tmp_dir += System.getProperty("file.separator");
		}

		// Acquire MD5
		try {
			lock_file = new File(tmp_dir + DigestUtils.md5Hex(key) + ".app_lock");
		} catch (Exception ex) {
		}

		// MD5 acquire fail
		if (lock_file == null) {
			lock_file = new File(tmp_dir + key + ".app_lock");
		}

		lock_stream = new FileOutputStream(lock_file);

		String f_content = "Java AppLock Object\r\nLocked by key: " + key
				+ "\r\n";
		lock_stream.write(f_content.getBytes());

		lock_channel = lock_stream.getChannel();

		lock = lock_channel.tryLock();

		if (lock == null) {
			throw new Exception("Can't create Lock");
		}
	}

	/**
	 * Remove Lock. Now another application instance can gain lock.
	 * 
	 * @throws Throwable
	 */
	private void release() throws Throwable {
		if (lock != null) {
			lock.release();
		}

		if (lock_channel != null) {
			lock_channel.close();
		}

		if (lock_stream != null) {
			lock_stream.close();
		}

		if (lock_file != null) {
			lock_file.delete();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		this.release();
		super.finalize();
	}

	private static AppLock instance;

	/**
	 * Set Lock based on input key Method can be run only one time per
	 * application. Second calls will be ignored.
	 * 
	 * @param key
	 *            Lock key
	 */
	public static boolean setLock(String key) {
		if (instance != null) {
			return true;
		}

		try {
			instance = new AppLock(key);
		} catch (Exception ex) {
			instance = null;
			return false;
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				AppLock.releaseLock();
			}
		});
		return true;
	}

	/**
	 * Try to release Lock. After releasing you can not user AppLock again in
	 * application.
	 */
	public static void releaseLock() {
		if (instance == null) {
			return;
		}
		try {
			instance.release();
		} catch (Throwable ex) {
		}
	}
}
