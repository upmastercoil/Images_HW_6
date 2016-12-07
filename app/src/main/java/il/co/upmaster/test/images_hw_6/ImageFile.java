package il.co.upmaster.test.images_hw_6;

public class ImageFile {
    private String name;
    private long id;

    public ImageFile(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public ImageFile(String name) {
        new ImageFile(name, -1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
