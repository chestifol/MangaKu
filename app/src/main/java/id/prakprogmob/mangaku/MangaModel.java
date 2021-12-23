package id.prakprogmob.mangaku;

import android.os.Parcel;
import android.os.Parcelable;

public class MangaModel implements Parcelable {

    private String textjudulManga;
    private String textsinopsisManga;
    private String textjenisManga;
    private String textgenreManga;
    private String textulasanManga;
    private String textratingManga;

    public MangaModel(String textjudulManga, String textjenisManga, String textgenreManga, String textsinopsisManga, String textratingManga, String textulasanManga){
        this.textjudulManga = textjudulManga;
        this.textjenisManga = textjenisManga;
        this.textgenreManga= textgenreManga;
        this.textsinopsisManga = textsinopsisManga;
        this.textratingManga = textratingManga;
        this.textulasanManga= textulasanManga;
    }

    protected MangaModel(Parcel in) {
        textjudulManga = in.readString();
        textsinopsisManga = in.readString();
        textjenisManga = in.readString();
        textgenreManga = in.readString();
        textulasanManga = in.readString();
        textratingManga = in.readString();
    }

    public static final Creator<MangaModel> CREATOR = new Creator<MangaModel>() {
        @Override
        public MangaModel createFromParcel(Parcel in) {
            return new MangaModel(in);
        }

        @Override
        public MangaModel[] newArray(int size) {
            return new MangaModel[size];
        }
    };

    public String getTextjudulManga() {
        return textjudulManga;
    }

    public String getTextsinopsisManga() {
        return textsinopsisManga;
    }

    public String getTextjenisManga() {
        return textjenisManga;
    }

    public String getTextgenreManga() {
        return textgenreManga;
    }

    public String getTextulasanManga() {
        return textulasanManga;
    }

    public String getTextratingManga() {
        return textratingManga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(textjudulManga);
        dest.writeString(textsinopsisManga);
        dest.writeString(textjenisManga);
        dest.writeString(textgenreManga);
        dest.writeString(textulasanManga);
        dest.writeString(textratingManga);
    }
}

