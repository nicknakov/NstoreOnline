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
	
	//----------- java
	
	package converters;
import java.text.DecimalFormat;
import java.util.Arrays;

//---  https://gist.github.com/wqweto/d7ca68a5fd0368c08158

public final class ToWordsModuleNiki {

	public static String ToWords(Double dblValue, String Measure) {
		return ToWords(dblValue, Measure, "");
	}

	public static String ToWords(Double dblValue) {
		return ToWords(dblValue, "", "");
	}

//VB TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: Public Function ToWords(ByVal dblValue As Double, Optional Measure As Object = Nothing, Optional Gender As String = Nothing) As String
	public static String ToWords(Double dblValue, String Measure, String Gender) {
		DecimalFormat df = new DecimalFormat("0.00##");
		String[] vDigits = null;
		String[] vGenderDigits = null;
		String[] vValue = null;
		String[] milioni = { "милион", "милиард", "трилион", "квадрилион" };
		int lIdx = 1;
		String lDigit = "";
		String sResult = "";
		String sString = "000000000000000000";

		// --- init digits (incl. gender ones)
		vDigits = "нула едно две три четири пет шест седем осем девет".split("[ ]", -1);
		vGenderDigits = Arrays.copyOf(vDigits, vDigits.length);
//VB TO JAVA CONVERTER NOTE: The following VB 'Select Case' included either a non-ordinal switch expression or non-ordinal, range-type, or non-constant 'Case' expressions and was converted to Java 'if-else' logic:
//	Select Case Left(Gender, 1)
//ORIGINAL LINE: Case String.Empty, "M"
		if ((Gender.substring(0, 1).equals("")) || (Gender.substring(0, 1).equals("M"))) {
			vGenderDigits[1] = "един";
			vGenderDigits[2] = "два";
		}
//ORIGINAL LINE: Case "F"
		else if (Gender.substring(0, 1).equals("F")) {
			vGenderDigits[1] = "една";
		}
		// --- split input value on decimal point and pad w/ zeroes
		// vValue = "0.0".substring(1, 2); // this line returns .
		// double absDBVALUE = Math.abs(dblValue);
		vValue = (df.format(Math.abs(dblValue)).toString()).split("\\.");
		vValue[0] = (sString + vValue[0]).substring((sString + vValue[0]).length() - 18);
		System.out.println("vValue: " + vValue[0]);
		// --- loop
		while(lIdx <= vValue[0].length()) {
			if (lIdx <= 3) {
				lDigit = vValue[0].substring((vValue[0].length()) - lIdx, (vValue[0].length()) - lIdx + 1);
				System.out.println("lDigit: " + lDigit);
				// lDigit = String.valueOf(vValue[0].charAt(vValue[0].length() - lIdx + 1));
			} else {
				lDigit = vValue[0].substring(vValue[0].length() - lIdx, vValue[0].length() - lIdx +3);
				lIdx = lIdx + 2;
			}
			//if (Integer.valueOf(lDigit) != 0) {
			if (!lDigit.equals("0")) {	
			// --- separate by space (first time prepend "и" too)
				System.out.println("sResultLength: " + sResult.length());
				if (sResult.length() != 0 && (lIdx != 2 || Integer.valueOf(lDigit) != 1)) {
					if (!sResult.contains(" и ")) {
						sResult = " и " + sResult;
					} else {
						sResult = " " + sResult;
					}
				}
				switch (lIdx) {
				case 1:
					sResult = vGenderDigits[Integer.valueOf(lDigit)] + sResult;
					System.out.println("switch case 1: " + sResult);
					break;
				case 2:
					if (Integer.valueOf(lDigit) == 1) {
						// --- 11 to 19 special wordforms
						if (sResult.length() != 0) {
							sResult = sResult.trim().replace(vGenderDigits[1], "еди");
							sResult = sResult.replace(vGenderDigits[2], "два") + "надесет";
						} else {
							sResult = "десет";
						}
					} else {
						System.out.println("lDigit: " + lDigit);
						sResult = ((lDigit.equals("2") ? "два":(vDigits[Integer.valueOf(lDigit)])) + "десет" + sResult);
						
					}
					System.out.println("switch case 2: " + sResult);
					break;
				case 3:
					// --- hundreds have special suffixes for 2 and 3
					switch (Integer.valueOf(lDigit)) {
					case 1:
						sResult = "сто" + sResult;
						break;
					case 2:
						sResult = vDigits[Integer.valueOf(lDigit)] + "ста" + sResult;
						break;
					case 3:
						sResult = vDigits[Integer.valueOf(lDigit)] + "ста" + sResult;
						break;
					default:
						sResult = vDigits[Integer.valueOf(lDigit)] + "стотин" + sResult;
						break;
					}
					System.out.println("switch case 3: " + sResult);
					break;
				case 6:
					// --- thousands are in feminine gender
					switch (Integer.valueOf(lDigit)) {
					case 1:
						sResult = "хиляда" + sResult;
						break;
					default:
						System.out.println("case 6: lDigit: " + lDigit + ", sResult= " + sResult);
						sResult = ToWords(Double.valueOf(lDigit), null, null) + " хиляди" + sResult;
						break;
					}
					break;
				case 9:
				case 12:
				case 15:
					// --- no special cases for bigger values
					sResult = ToWords(Double.valueOf(lDigit), "") + " " + milioni[(lIdx - 9) / 3]
							+ (Integer.valueOf(lDigit) != 1 ? "а" : "") + sResult;
					break;
				}
			}
			lIdx++;
		}
		// --- end loop
		// --- handle zero and negative values
		// - HERE
		if ((sResult == null ? 0 : sResult.length()) == 0) {
			sResult = vDigits[0];
		}
		if (dblValue < 0) {
			sResult = "минус " + sResult;
		}
		// --- apply measure (use String.Empty for none)
		if (Measure == null) {
			Measure = "лв.|ст.";
			Gender = "MF";
		}
		/*
		 * if ((Measure == null ? 0 : Measure.length()) != 0){ if
		 * (sResult.substring(sResult.length() - (vDigits(0).Length)).equals(vDigits(0))
		 * && Microsoft.VisualBasic.Conversion.Val(vValue(1)) != 0 &&
		 * (Measure.toString().indexOf("|") + 1) > 0) { sResult = ToWords(((dblValue <
		 * 0) ? -1 : 1) * Microsoft.VisualBasic.Conversion.Val(vValue(1)),
		 * Measure.toString().split('|')[1], Gender.substring(1)); } else { sResult =
		 * sResult + " " + Measure.toString().split("[|]", -1)[0]; if
		 * (Microsoft.VisualBasic.Conversion.Val(vValue(1)) != 0) { sResult = sResult +
		 * " и " + Microsoft.VisualBasic.Conversion.Val(vValue(1)); if
		 * (Measure.toString().indexOf("|") + 1 > 0) { sResult = sResult + " " +
		 * Measure.toString().split("[|]", -1)[1]; } } sResult = sResult.substring(0,
		 * 1).toUpperCase() + sResult.substring(1); } }
		 */
		return sResult;
	}

}

//Helper class added by VB to Java Converter:



	
	
	
