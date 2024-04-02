import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {

	public static void main(String[] args) {

		List<SearchDNA> dnas = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String filename = "dna-" + i + ".txt";
			dnas.add(new SearchDNA(filename));
		}

		// Cria ExecutorService para gerenciar threads.
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < dnas.size(); i++) {
			executorService.execute(dnas.get(i));
		}

		executorService.shutdown();
		System.out.println("Fim main");

	}

}
