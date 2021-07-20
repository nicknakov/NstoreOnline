# NstoreOnline

${pageContext.request.contextPath}/

	<dependency>
	  	<groupId>javax.servlet.jsp</groupId>
	  	<artifactId>jsp-api</artifactId>
	  	<version>2.2</version>
	</dependency>
	
	
	//-------------- Resizing Uploaded Image
				
					
			BufferedImage bImage  = resizeImage(convert(file));
			System.out.println("=== convert then resize finished! - In UploadFileHelper");
				
			byte[] bytes = imageInBytes(bImage);
			System.out.println("=== converted to bytes - In UploadFileHelper");
	
	//----- Insert these methods

	private static BufferedImage resizeImage(File file) throws IOException{
		BufferedImage originalImage = ImageIO.read(file);
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		BufferedImage resizedImage = new BufferedImage(300, 300, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 300, 300, null);
		g.dispose();

		return resizedImage;
	}
	
	private static File convert(MultipartFile file) throws IOException	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
	private static byte[] imageInBytes(BufferedImage image) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( image, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	//----------------
