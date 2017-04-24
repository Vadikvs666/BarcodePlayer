/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

/**
 *
 * @author vadim
 */
public class Barcode {
    private int id;
    private String barcode;
    private String title;
    private double price;
    private int count;
    private String video;

    public Barcode(String barcode, String video) {
        this.barcode = barcode;
        this.video = video;
    }

    public Barcode(int id, String barcode, String title, double price, int count, String video) {
        this.id = id;
        this.barcode = barcode;
        this.title = title;
        this.price = price;
        this.count = count;
        this.video = video;
    }

    public Barcode(String barcode, String title, double price, int count, String video) {
        this.barcode = barcode;
        this.title = title;
        this.price = price;
        this.count = count;
        this.video = video;
    }

    Barcode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    public void setId(int id) {
        this.id = id;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getVideo() {
        return video;
    }
}
