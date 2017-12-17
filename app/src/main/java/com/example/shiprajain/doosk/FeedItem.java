/*
* Model Class for providing data to the recycler views after parsing the json.
* Url is created using farm, server, id, and secret field.
* Original size URL : http://farm{farm}.staticflickr.com/{server}/{id}_{secret}.jpg
* Thumbnail URL : http://farm{farm}.staticflickr.com/{server}/{id}_{secret}_t_d.jpg
* */

package com.example.shiprajain.doosk;

public class FeedItem {
    private String farm;
    private String server;
    private String id;
    private String secret;
    public FeedItem() {}

    public FeedItem(String farm, String server, String id, String secret) {
        this.farm = farm;
        this.server = server;
        this.id = id;
        this.secret = secret;
    }

    public String getFarm() {
        return farm;
    }
    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getThumbnailUrl() {
        return "http://farm"+getFarm()+".staticflickr.com/"+getServer()+"/"+getId()+"_"+getSecret()+"_t.jpg";
    }

    public String getOriginalSizeUrl() {
        return "http://farm"+getFarm()+".staticflickr.com/"+getServer()+"/"+getId()+"_"+getSecret()+".jpg";
    }
}
