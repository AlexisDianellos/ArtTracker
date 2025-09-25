package com.artgallery.tracker;

import com.artgallery.tracker.s3.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ArtGalleryTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtGalleryTrackerApplication.class, args);
	}
	/*@Value("${AWS_BUCKET_NAME}")
	private String bucketName;
	@Bean
	CommandLineRunner testS3(S3Service s3Service) {
		return args -> {
			try {
				// 1. Upload test text file
				s3Service.putObject(bucketName, "foo","helloworld".getBytes());
				System.out.println(" Uploaded test object with key: " );
				// 2. Download it back
				byte[] downloaded = s3Service.getObject("foo",bucketName);
				System.out.println("Downloaded object content: " + new String(downloaded));

			} catch (Exception e) {
				System.err.println(" S3 test failed: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}*/
}
