# Doosk

This is a simple app to fetch images from server and display in a recycler view.

What tha app does:
• Make a single request to Flickr’s API and parse results
• Use parsed results to determine the images to display
• Build a single screen app that scrolls vertically through dog images

Framework Used:
Doosk uses Google's Volley Framework to make network calls for fetching data to create image urls. The thumbnail URL and bitmap of an
image are cached in application memory. LRUCache class is used to manage Image URL and bitmap map. When an image is clicked, another 
network call is made to fetch original size image.

Yahoo Flickr API Key
d5c7df3552b89d13fe311eb42715b510

Dog Photos Query:
Get 40 Dog pictures from Flickr
select * from flickr.photos.search(40) where text="Dog" and
api_key="d5c7df3552b89d13fe311eb42715b510"

The REST Query:
https://query.yahooapis.com/v1/public/yql?
q=select%20*%20from%20flickr.photos.search(20)%20where%20text%3
D%22Dog%22%20%20and%20api_key%3D%22d5c7df3552b89d13fe31
1eb42715b510%22&format=json&diagnostics=true&callback=

Parsing Individual Pictures
Original size:
http://farm{farm}.staticflickr.com/{server}/{id}_{secret}.jpg
Thumbnail:
http://farm{farm}.staticflickr.com/{server}/{id}_{secret}_t_d.jpg
Where {farm}, {server}, {id}, {secret} are inputs from the XML/JSON
response.
