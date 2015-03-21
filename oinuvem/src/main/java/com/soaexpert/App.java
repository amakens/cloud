package com.soaexpert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Hello world!
 *
 */
public class App {
	
	private static final String NOME_DO_OBJETO = "hello.text";
	
	private static final String NOME_DO_BUCKET = "hello-s3-world-makens";
	
    public static void main( String[] args ) {
    	AmazonS3 s3 = new AmazonS3Client();
    	
//    	s3.setEndpoint("sa-east-1");//opcional - us-west-2
    	
    	//Cria um Bucket
    	
    	Bucket bucket = s3.createBucket(NOME_DO_BUCKET);
    	
    	//aqui, definimos o que iremos salvar no S3, alem do metadado do objeto(Content-Type)
    	
    	InputStream is = new ByteArrayInputStream("oi, mundo!".getBytes());
    	ObjectMetadata metadata = new ObjectMetadata();
    	
    	metadata.setContentType("text/plain");
    	
    	//Constroi o Request, e submete a AWS
    	
    	PutObjectRequest request = new PutObjectRequest(bucket.getName(), NOME_DO_OBJETO, is, metadata);
    	
    	s3.putObject(request);
    	
    	//Ja esta salvo, mas iremos gerar um endereco para exibir - Para tal, o endereco possui uma data de validade. Iremos criar uma valida para 3 meses
    	
    	Date dataDeVallidade = null;
    	
    	{
    		Calendar calendar = Calendar.getInstance();
    		
    		calendar.add(Calendar.MONTH, 3);
    		
    		dataDeVallidade = calendar.getTime();
    	}
    	
    	//Gera o endereco e o exibe
    	String url = s3.generatePresignedUrl(NOME_DO_BUCKET, NOME_DO_OBJETO, dataDeVallidade).toExternalForm();
    	
    	System.out.println("Por favor, abra o seguinte endereço:");
    	
    	System.out.println(url);
    }
}
