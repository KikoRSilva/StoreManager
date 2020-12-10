package woo.core;

public class Book extends Product{
    
    /**
     *
     */
    private static final long serialVersionUID = 6684985915646921401L;
    
    private String _title;
    private String _author;
    private String _ISBN;

    public Book(String id, int price, int criticalValue, Supplier s, String title, String author, String isbn) {
    	
        super(id, price, criticalValue, s);
        _title = title;
        _author = author;
        _ISBN = isbn;
    }

    /////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////
    public String getTitle() { return _title; }

    public String getAuthor() { return _author; }
    
    public String getISBN() { return _ISBN; }

    public String getNameMode() { return "BookMode"; }
    /////////////////////////////////////////////// OTHERS FUNCTIONS ////////////////////////////////////////////////////
    public String toString() {
        return "BOOK" + " | " + super.getId() + " | " + super.getSupplier().getId() + " | " + super.getPrice() + " | " + super.getCriticalValue() + " | " + super.getStock() + " | " + getTitle() + " | " + getAuthor() + " | " + getISBN();
    }
}
