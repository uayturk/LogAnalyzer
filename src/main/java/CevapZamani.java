public class CevapZamani {

    private String cevapZaman;
    private String id;
    private String Request_type;


    public CevapZamani(String cevapZaman, String id ,String Request_type) {
        this.cevapZaman = cevapZaman;
        this.id = id;
        this.Request_type=Request_type;

    }

    public String getCevapZaman() {
        return cevapZaman;
    }

    public void setCevapZaman(String cevapZaman) {
        this.cevapZaman = cevapZaman;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest_type() {
        return Request_type;
    }

    public void setRequest_type(String request_type) {
        Request_type = request_type;
    }
}
