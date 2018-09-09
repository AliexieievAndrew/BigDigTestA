package comw.example.user.testtaska.Body;

import android.database.Cursor;

import comw.example.user.testtaska.DataBase.DataBaseProvider;

public class ImageLinkObject {
    private int id;
    private int status;
    private String link;
    private long time;

    public ImageLinkObject(int id, int status, String link, long time) {
        this.id = id;
        this.status = status;
        this.link = link;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public static ImageLinkObject fromCursor(Cursor cursor) {
        ImageLinkObject imageLinkObject = new ImageLinkObject(
                cursor.getInt(cursor.getColumnIndex(DataBaseProvider.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(DataBaseProvider.COLUMN_STATUS)),
                cursor.getString(cursor.getColumnIndex(DataBaseProvider.COLUMN_LINK)),
                cursor.getLong(cursor.getColumnIndex(DataBaseProvider.COLUMN_TIME_OF_USE))
        );
        return imageLinkObject;
    }

    @Override
    public String toString() {
        return "ImageLinkObject{" +
                "id=" + id +
                ", status=" + status +
                ", link='" + link + '\'' +
                ", time=" + time +
                '}';
    }
}