/**** on va ici implémenter la transformée de Fourier rapide 1D ****/

public class FFT_1D {

	//"combine" c1 et c2 selon la formule vue en TD
	// c1 et c2 sont de même taille
	// la taille du résultat est le double de la taille de c1
	public static CpxTab combine(CpxTab c1, CpxTab c2) {
		assert (c1.taille()==c2.taille()) : "combine: c1 et c2 ne sont pas de même taille, taille c1="+c1.taille()+" taille c2="+c2.taille();
		//A FAIRE
		double w_n_pr = Math.cos(2*Math.PI/c1.taille());
		double w_n_pi = Math.sin(2*Math.PI/c1.taille());
		CpxTab out = new CpxTab(2*c1.taille());
		for(int k = 0; k < 2*c1.taille()-1; k++) {
			out.set_p_reel(k, c1.get_p_reel(k)+w_n_pr*c2.get_p_reel(k));
			out.set_p_imag(k, c1.get_p_imag(k)+w_n_pi*c2.get_p_imag(k));
			out.set_p_reel(k+c1.taille()/2, c1.get_p_reel(k+c1.taille()/2)+w_n_pr*c2.get_p_reel(k+c1.taille()/2));
			out.set_p_imag(k+c1.taille()/2, c1.get_p_imag(k+c1.taille()/2)+w_n_pi*c2.get_p_imag(k+c1.taille()/2));
			w_n_pr = w_n_pr * Math.cos(2*Math.PI/c1.taille());
			w_n_pi = w_n_pi * Math.sin(2*Math.PI/c1.taille());
		}
		return out;
	}

	//renvoie la TFD d'un tableau de complexes
	//la taille de x doit être une puissance de 2
	public static CpxTab FFT(CpxTab x) {
		//A FAIRE : Test d'arrêt

		assert (x.taille()%2==0) : "FFT: la taille de x doit être une puissance de 2";

		//A FAIRE : Décomposition en "pair" et "impair" et appel récursif
		int n = x.taille();
		if(n==1) {
			return x;
		}
		double w_n_pr = Math.cos(2*Math.PI/x.taille());
		double w_n_pi = Math.sin(2*Math.PI/x.taille());
		double w_n = 1;
		CpxTab xpair = new CpxTab(n/2);
		for(int j = 0; j < n; j = j+2) {
			xpair.set_p_reel(j/2, x.get_p_reel(j));
			xpair.set_p_imag(j/2, x.get_p_imag(j));
		}
		CpxTab ximpair = new CpxTab(n/2);
		for(int k = 0; k < n; k = k+2) {
			ximpair.set_p_reel(k/2, x.get_p_reel(k+1));
			ximpair.set_p_imag(k/2, x.get_p_imag(k+1));
		}
		CpxTab ypair = FFT(xpair);
		CpxTab yimpair = FFT(ximpair);
		CpxTab y = combine(ypair,yimpair);
		return y;
	}

	//renvoie la TFD d'un tableau de réels
	//la taille de x doit être une puissance de 2
	public static CpxTab FFT(double[] x) {
		return FFT(new CpxTab(x));
	}

	//renvoie la transformée de Fourier inverse de y
	public static CpxTab FFT_inverse(CpxTab y) {
		//A FAIRE
		return FFT(y.conjugue()).conjugue();
	}

	//calcule le produit de deux polynômes en utilisant la FFT
	//tab1 et tab2, sont les coefficients de ces polynômes
	// CpxTab sera le tableau des coefficients du polynôme produit (purement réel)
	public static CpxTab multiplication_polynome_viaFFT(double[] tab1, double[] tab2) {

		//on commence par doubler la taille des vecteurs en rajoutant des zéros à la fin (cf TD)
		double[] t1 = new double[2*tab1.length], t2 = new double[2*tab1.length];
		for (int i = 0; i < tab1.length; i++) {
			t1[i] = tab1[i];
			t2[i] = tab2[i];
		}

		//A COMPLETER !!
		return FFT_inverse(CpxTab.multiplie(FFT(t1),FFT(t2)));
	}


	//renvoie un tableau de réels aléatoires
	//utile pour tester la multiplication de polynômes
	public static double[] random(int n) {
		double[] t = new double[n];

		for (int i = 0; i < n; i++)
			t[i] = Math.random();
		return t;
	}

	//effectue la multiplication de polynômes représentés par coefficients
	// p1, p2 les coefficients des deux polynômes P1 et P2
	// renvoie les coefficients du polynôme P1*P2
	private static double [] multiplication_polynome_viaCoeff(double[] p1, double[] p2){

		int n = p1.length + p2.length - 1;
		double a,b;
		double [] out = new double[n];
		for (int k = 0; k < n; k++) {
			for (int i = 0; i <= k; i++) {
				a = (i<p1.length) ? p1[i]:0;
				b = (k-i<p2.length) ? p2[k-i] : 0;
				out[k] += a*b;
			}
		}
		return out;
	}


	//affiche un tableau de réels
	private static void afficher(double [] t){
		System.out.print("[");
		for(int k=0;k<t.length;k++){
			System.out.print(t[k]);
			if (k<(t.length-1))
				System.out.print(" ");
		}
		System.out.println("]");
	}

	public static void main(String[] args) {
		double[] t5 = {1,2,3,4};

		/* Exo 2: calculez et affichez TFD(1,2,3,4) */
		//A FAIRE
		FFT(t5).toString();

		/* Exo 3: calculez et affichez TFD_inverse(TFD(1,2,3,4)) */
		//A FAIRE
		FFT_inverse(FFT(t5)).toString();

		/* Exo 4: multiplication polynomiale, vérification*/
		/* A(X) = 2 et B(X)=-3 */
		//A FAIRE		

		/* A(X) = 2+X et B(X)= -3+2X */
		//A FAIRE					

		/* A(X) = 1 + 2X + 3X^2 + 4X^3 et B(X) = -3 + 2X - 5 X^2*/
		/*
		System.out.println("-----------------------------------------------------");
		System.out.println("   Comparaison des 2 méthodes de multiplications polynomiales");
		double[] t6 = {-3,2,-5,0};
		System.out.println("mult via FFT  --> " + multiplication_polynome_viaFFT(t5, t6));
		System.out.print(  "mult via coeff -> ");
		afficher(multiplication_polynome_viaCoeff(t5, t6));
		 */

		/* Exo 5: comparaison des temps de calculs */
		/*
		// Pour étude du temps de calcul 
		int n = 256;  // taille des polynômes à multiplier (testez différentes valeurs en gardant des puissances de 2)

		System.out.println("Temps de calcul pour n="+n);
		double[] tab1 =random(n),tab2 = random(n);
		long date1, date2;
		date1 = System.currentTimeMillis();
		multiplication_polynome_viaCoeff(tab1, tab2);
		date2 = System.currentTimeMillis();
		System.out.println("   via Coeff: " + (date2 - date1));

		date1 = System.currentTimeMillis();
		multiplication_polynome_viaFFT(tab1, tab2);
		date2 = System.currentTimeMillis();
		System.out.println("   via FFT  : " + (date2 - date1));
		 */

	}

}
