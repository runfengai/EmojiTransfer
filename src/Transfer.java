import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 将json复杂格式转为简单格式
 * 
 * @author Jarry
 * 
 */
public class Transfer {
	public static final String ORIGINAL_JSON = "emojis.json";
	public static final String RESULT_JSON = "emoji.json";

	public static void main(String[] args) {
		File f = new File(ORIGINAL_JSON);
		new Transfer().trans(f);
	}

	public void trans(File f) {
		if (!f.exists()) {
			System.out.println("不存在！");
			return;
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		BufferedWriter bw = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(RESULT_JSON), "UTF-8"));
			// bw.write(res);
			String tmp = "";
			boolean isTag = false;
			while ((tmp = reader.readLine()) != null) {
				if (tmp.equals("    ],")) {
					tmp = "    ]";
				}
				if (tmp.equals("    \"tags\": []"))
					continue;
				if (tmp.startsWith("    \"emojiChar"))
					continue;

				if (tmp.startsWith("    \"description")) {

				} else if (isTag && tmp.equals("    ]")) {
					isTag = !isTag;
				} else if (tmp.startsWith("    \"tags")) {// tag去掉
					isTag = !isTag;
				} else if (!isTag) {
					sb.append(tmp);
					sb.append("\n");
					bw.write(tmp);
					bw.write("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		// Gson gson = new Gson();
		// List<Emoji> list = gson.fromJson(sb.toString(),
		// new TypeToken<List<Emoji>>() {
		// }.getType());
		// // 将数据写入emojis.json
		// String res = gson.toJson(list);
		// System.out.println(res);

		try {

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
