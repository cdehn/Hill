public class hillCipher {
	public static void main(String[] args){
		String cipher = "rccd";
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 4; k++){
					for(int l = 0; l < 4; l++){
						int[][]keymatrix = {{i,j},{k,l}};
						int det = keymatrix[0][0] * keymatrix[1][1] - keymatrix[1][0] * keymatrix[0][1];
						System.out.println(det);
						if(det % 2 != 0 && det % 13 != 0){
							int[][]inverse = cofact(keymatrix, 2, keymatrix);
							divide(cipher, 2, inverse);
						}
					}
				}
			}
		}

	}

	public static int determinant(int A[][], int N)
    {
        int res;
        if (N == 1)
            res = A[0][0];
        else if (N == 2)
        {
            res = A[0][0] * A[1][1] - A[1][0] * A[0][1];
        }
        else
        {
            res = 0;
            for (int j1 = 0; j1 < N; j1++)
            {
                int m[][] = new int[N - 1][N - 1];
                for (int i = 1; i < N; i++)
                {
                    int j2 = 0;
                    for (int j = 0; j < N; j++)
                    {
                        if (j == j1)
                            continue;
                        m[i - 1][j2] = A[i][j];
                        j2++;
                    }
                }
                res += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1]
                        * determinant(m, N - 1);
            }
        }
        return res;
    }
	public static int[][] cofact(int num[][], int f, int[][]keymatrix)
    {
        int b[][], fac[][];
        b = new int[f][f];
        fac = new int[f][f];
        int p, q, m, n, i, j;
        for (q = 0; q < f; q++)
        {
            for (p = 0; p < f; p++)
            {
                m = 0;
                n = 0;
                for (i = 0; i < f; i++)
                {
                    for (j = 0; j < f; j++)
                    {
                        b[i][j] = 0;
                        if (i != q && j != p)
                        {
                            b[m][n] = num[i][j];
                            if (n < (f - 2))
                                n++;
                            else
                            {
                                n = 0;
                                m++;
                            }
                        }
                    }
                }
                fac[q][p] = (int) Math.pow(-1, q + p) * determinant(b, f - 1);
            }
        }
 
        int i1, j1;
        int b1[][], inv[][];
        b1 = new int[f][f];
        inv = new int[f][f];
        int d = determinant(keymatrix, f);
        int mi = mi(d % 26);
        mi %= 26;
        if (mi < 0)
            mi += 26;
        for (i1 = 0; i1 < f; i1++)
        {
            for (j1 = 0; j1 < f; j1++)
            {
                b1[i1][j1] = fac[j1][i1];
            }
        }
        for (i1 = 0; i1 < f; i1++)
        {
            for (j1 = 0; j1 < f; j1++)
            {
                inv[i1][j1] = b1[i1][j1] % 26;
                if (inv[i1][j1] < 0)
                    inv[i1][j1] += 26;
                inv[i1][j1] *= mi;
                inv[i1][j1] %= 26;
            }
        }
        System.out.println("\nInverse key:");
 
        String invkey = "";
        for (int a = 0; a < f; a++)
        {
            for (int z = 0; z < f; z++)
            {
                invkey += (char) (inv[a][z] + 97);
            }
        }
        System.out.println(invkey);
        
        return inv;
    }
    public static int mi(int d)
    {
        int q, r1, r2, r, t1, t2, t;
        r1 = 26;
        r2 = d;
        t1 = 0;
        t2 = 1;
        while (r1 != 1 && r2 != 0)
        {
            q = r1 / r2;
            r = r1 % r2;
            t = t1 - (t2 * q);
            r1 = r2;
            r2 = r;
            t1 = t2;
            t2 = t;
        }
        return (t1 + t2);
    }
	public static void divide(String temp, int s, int[][]inv){
        while (temp.length() > s) {
            String sub = temp.substring(0, s);
            temp = temp.substring(s, temp.length());
            linetomatrix(sub, inv);
        }
        if (temp.length() == s)
            linetomatrix(temp, inv);
        else if (temp.length() < s)
        {
            for (int i = temp.length(); i < s; i++)
                temp = temp + 'x';
            linetomatrix(temp, inv);
        }
    }
 
    public static void linetomatrix(String line, int[][]inv){
        int[]linematrix = new int[line.length()];
        for (int i = 0; i < line.length(); i++)
        {
            linematrix[i] = ((int) line.charAt(i)) - 97;
        }
 
        
        int[]resultmatrix = new int[line.length()];
        
        for (int i = 0; i < line.length(); i++){
            for (int j = 0; j < line.length(); j++){
                resultmatrix[i] = inv[i][j] * linematrix[j];
            }
            resultmatrix[i] %= 26;
        }
        result(line.length(), resultmatrix);
    }
 
    public static void result(int len, int[]resultmatrix){
        String result = "";
        for (int i = 0; i < len; i++){
            result += (char) (resultmatrix[i] + 97);
        }
        System.out.println("Plain text:");
        System.out.println(result);
    }
    
}