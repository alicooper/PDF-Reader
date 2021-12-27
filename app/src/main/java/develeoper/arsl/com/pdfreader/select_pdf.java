package develeoper.arsl.com.pdfreader;

import java.lang.ref.SoftReference;

public class select_pdf {
    String path_file;
    String name_file;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "select_pdf{" +
                "path_file='" + path_file + '\'' +
                ", name_file='" + name_file + '\'' +
                '}';
    }

    public String getPath_file() {
        return path_file;
    }

    public void setPath_file(String path_file) {
        this.path_file = path_file;
    }

    public String getName_file() {
        return name_file;
    }

    public void setName_file(String name_file) {
        this.name_file = name_file;
    }

    public select_pdf(String path_file, String name_file, String id) {
        this.path_file = path_file;
        this.name_file = name_file;
        this.id = id;
    }
}
