function getImage(ISBN) {    
    var data ="";

    var request = new XMLHttpRequest();
    request.open('GET', 'https://www.googleapis.com/books/v1/volumes?q=isbn:' + ISBN, true);

    request.onload = function() {
      if (request.status >= 200 && request.status < 400) {
        resp = request.responseText;
        data = JSON.parse(resp);
        var image = document.getElementById("cover_img");
        image.innerHTML = "<img class='cover' src='" + data.items[0].volumeInfo.imageLinks.thumbnail + "' /><br><input name='cover_image' value='" + data.items[0].volumeInfo.imageLinks.thumbnail + "' />";
        } else {
          
      }
    };

    request.send();
  }