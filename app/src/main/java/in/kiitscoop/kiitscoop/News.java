package in.kiitscoop.kiitscoop;


public class News {
    String source;
    String newsTitle;
    String newsLink;
    String newsContent;
    String name;
    String logoUrl;
    String imageUrl;
    String id;
    String category;

    public News(){

    }

    public News(String source, String newsTitle, String newsLink, String newsContent, String name, String logoUrl, String imageUrl, String id, String category) {
        this.source = source;
        this.newsTitle = newsTitle;
        this.newsLink = newsLink;
        this.newsContent = newsContent;
        this.name = name;
        this.logoUrl = logoUrl;
        this.imageUrl = imageUrl;
        this.id = id;
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() { return name; }
}
