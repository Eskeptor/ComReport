package ex7;

import java.io.*;

public class ReadData {
    private File mFile;
    private int mCount;
    private int[][] mDatas;
    ReadData(final String _file) {
        mFile = new File(_file);
        init();
    }

    private void init() {
        BufferedReader bufferedReader = null;
        boolean isFirst = true;
        int idx = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader(mFile));
            while(true) {
                String line = bufferedReader.readLine();

                if(line == null || line.equals(""))
                    break;

                if(isFirst) {
                    mCount = Integer.parseInt(line);
                    mDatas = new int[countLines()][2];
                    isFirst = false;
                } else {
                    String[] str = line.split(" ");
                    mDatas[idx][0] = Integer.parseInt(str[0]);
                    mDatas[idx][1] = Integer.parseInt(str[1]);
                    idx++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null)
                try { bufferedReader.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public int getmCount() {
        return mCount;
    }

    public int getmData(final int _idx1, final int _idx2) {
        if(_idx1 < mCount) {
            return mDatas[_idx1][_idx2];
        } else {
            return -1;
        }
    }

    private int countLines() {
        BufferedInputStream bufferedInputStream = null;
        int lines = 0;
        boolean isEmpty = true;

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(mFile));
            byte[] bytes = new byte[1024];
            int ch = 0;

            while ((ch = bufferedInputStream.read(bytes)) != -1) {
                isEmpty = false;
                for (int i = 0; i <ch;i++)
                    if (bytes[i] == '\n')
                        lines++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferedInputStream != null)
                try { bufferedInputStream.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return (lines == 0 && !isEmpty) ? -1 : lines;
    }
}
