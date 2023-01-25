package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository2;
    @Autowired
    BlogRepository blogRepository;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        //create an image based on given parameters and add it to the imageList of given blog
        Image image=new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        List<Image>list=blog.getImageList();
        list.add(image);
        blog.setImageList(list);
        image.setBlog(blog);
        blogRepository.save(blog);
        return image;
    }

    public void deleteImage(Image image){
        imageRepository2.delete(image);

    }

    public Image findById(int id) {
       return imageRepository2.findById(id).get();
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        String dimensions=image.getDimensions();

        int I=dimensions.indexOf("x");
        int N=dimensions.length();
        int  R=Integer.valueOf(dimensions.substring(0,I));
        int C=Integer.valueOf(dimensions.substring(I+1,N));

        int i=screenDimensions.indexOf("x");
        int n=screenDimensions.length();
        int r=Integer.valueOf(screenDimensions.substring(0,i));
        int c=Integer.valueOf(screenDimensions.substring(i+1,n));

        int count=0;
        while(R>=r){
            R-=r;
            int col=C;
            while(col>=c){
                count++;
                col-=c;
            }
        }
        return count;

    }
}
