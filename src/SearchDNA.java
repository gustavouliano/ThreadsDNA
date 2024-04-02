import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchDNA implements Runnable {

	private static final Map<String, String> validCombination = new HashMap<>() {
		{
			put("A", "T");
			put("T", "A");
			put("C", "G");
			put("G", "C");
		}
	};
	private String fileName;
	private List<Integer> invalidLine = new ArrayList<>();
	private int validQuantity = 0;

	public SearchDNA(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void run() {
		try {

			FileReader ler;
			String filePath = new File("").getAbsolutePath().concat("\\src\\arquivosDNA\\" + fileName);
			ler = new FileReader(filePath);
			BufferedReader reader = new BufferedReader(ler);
			String linha;
			int cont = 0;
			while ((linha = reader.readLine()) != null) {
				cont++;
				if (this.isValid(linha)) {
					this.validQuantity++;
					this.addComplementary(linha);
					
				}
				else {
					this.invalidLine.add(cont);
					this.addInvalid(linha);					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.showResult();
	}

	public boolean isValid(String dna) {
		for (int i = 0; i < dna.length(); i++) {
			String value = String.valueOf(dna.charAt(i));
			if (!SearchDNA.validCombination.containsKey(value)) {
				return false;
			}
		}
		return true;
	}

	public void addComplementary(String dna) {
		String complementaryDna = "";
		for (int i = 0; i < dna.length(); i++) {
			String value = String.valueOf(dna.charAt(i));
			complementaryDna += SearchDNA.validCombination.get(value);
		}
		
		FileWriter fw;
		try {
			String filePath = new File("").getAbsolutePath().concat("\\src\\arquivosDNA\\resultado-" + fileName);
			fw = new FileWriter(filePath, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(complementaryDna);
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addInvalid(String dna) {
		FileWriter fw;
		try {
			String filePath = new File("").getAbsolutePath().concat("\\src\\arquivosDNA\\resultado-" + fileName);
			fw = new FileWriter(filePath, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("****FITA INVÁLIDA - " + dna);
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showResult() {
		System.out.println("===============================");
		System.out.println("Arquivo: " + this.fileName);
		System.out.println("Total de fitas: " + (this.validQuantity + this.invalidLine.size()));
		System.out.println("Total de fitas válidas: " + this.validQuantity);
		System.out.println("Total de fitas inválidas: " + this.invalidLine.size());
		if (this.invalidLine.size() > 0) {
			System.out.println("As linhas inválidas são " + this.invalidLine.toString());
		}
	}

}
