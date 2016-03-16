Fotag!
======
- Created by Dongwoo Son
- Created at March 3, 2016
- Email: d3son@uwaterloo.ca

Preview
-------
![alt text](http://dongwoo1005.github.io/image/Fotag.png "Fotag grid view")

How to make and run
-------------------
1. Go to src directory
2. Use Makefile
    1. Run "make" to compile the .java files
    2. Run "make run" to compile
    3. Run "make clean" to clean .class files

How to use it
-------------
### Toolbar:
- Add New Image: Click on the add image icon to load image from desktop
    It supports all the image files supported by Java's ImageIcon class
- View Switch: Click on the Grid view icon or List view icon to switch view
- Rating Filter:
    When filter is greater than 0, it will only show the images with the same rating.
    Otherwise, it will show all the images
    How filter behaves:
        - On click to the empty star, it will fill stars up to and including the clicked empty star.
        - On click to the filled star, it will empty the stars up to and including the clicked filled star.

### Image:
- Rating: Click on stars to rate each image
- Enlarging:
    Click on the thumbnail image to get enlarged image on a new window.
    Click on the resized image to dismiss it.

### Behaviour:
- On Exit, all the image collection model will be saved in "./saved/saved.ser"
- On Relaunch, the saved file will be reloaded to be displayed in the image collection view.

More description
----------------
- Developed on Windows environment. But most if not all behaviour should be similar.
- In Mac environment, there is a bug in the grid view when collapsing the columns by resizing the frame:
    Some components(image) goes to the next row while some components stay.
- It takes some time to load bunch of images on relaunch. Loading indicator is not shown.
- Efficient filtering algorithm was not considered.
    When the filter value is 0 to display all the images, it take some time and the loading indicator is not displayed.

Credits
-------
- Icons made by Freepik from www.flaticon.com
- Logo made from www.squarespace.com
