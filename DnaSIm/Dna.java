package com.dna.main;

import java.util.Random;

public class Dna {
	String code;
	public int group_num;
	//constructor which takes a string of A's T's G's and C's; group_num for calculating values
	public Dna(String code, int group_num) {
		this.code = code;
		// System.out.println("code:" + code);
		genInts(group_num);
		genIndInts();
		this.group_num = group_num;
	}
	//constuctor for generating random code based off of size; group_num for calculating values
	public Dna(int size, int group_num) {
		code = genDna(size);
		// System.out.println("code:" + code);
		genInts(group_num);
		genIndInts();
		this.group_num = group_num;
	}
	//returns code in string form
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	//returns dna spliced with antother; original refers to the percent(0-100) likeness to the parent
	public Dna splice(Dna dna, float original) {
		return splice(dna.getCode(), original);
	}
	//dna splice with mutation percent rate (0 - 100)
	public Dna splice(Dna dna, float original, float mutate) {
		return splice(dna.getCode(), original, mutate);
	}
	//returns value from start grouping and end grouping
	public int getTotal(int start, int fin) {
		int gene = 0;
		for (int i = 0; i < fin - start; i++) {
			try {
				gene += dna_code[i + start];
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}
		return gene;
	}
	//splice with string
	public Dna splice(String dna, float original) {
		String spliced = " ";
		for (int i = 0; i < code.length(); i += group_num) {
			float per = r.nextInt(100) + r.nextFloat();
			if (per < original) {
				spliced += code.substring(i, i + group_num);
			} else {
				spliced += dna.substring(i, i + group_num);
			}
		}
		return new Dna(spliced.trim(), group_num);
	}
	//splice with string and mutation
	public Dna splice(String dna, float original, float mutation) {
		String spliced = " ";
		for (int i = 0; i < code.length(); i += group_num) {
			float per = r.nextInt(100) + r.nextFloat();
			float mutate = r.nextInt(100) + r.nextFloat();
			if (mutate > mutation) {
				if (per < original) {
					spliced += code.substring(i, i + group_num);
				} else {
					spliced += dna.substring(i, i + group_num);
				}
			} else {
				spliced += genMutation();
			}
		}
		return new Dna(spliced.trim(), group_num);
	}
	//splice with string but replace singular letters other than whole groups; can lead to more similar children
	public Dna splice(String dna, float original, float mutation, boolean single) {
		String spliced = " ";
		try{
		if (!single) {
			for (int i = 0; i < code.length(); i += group_num) {
				float per =  r.nextInt(100) + r.nextFloat();
				float mutate = r.nextInt(100) + r.nextFloat();
				if (mutate > mutation) {
					if (per < original) {
						spliced += code.substring(i, i + group_num);
					} else {
						spliced += dna.substring(i, i + group_num);
					}
				} else {
					spliced += genMutation();
				}
			}
		} else {
			for (int i = 0; i < code.length(); i += 1) {
				float per = 1 + r.nextInt(100) + r.nextFloat();
				float mutate = 1 + r.nextInt(100) + r.nextFloat();
				if (mutate > mutation) {
					if (per < original) {
						spliced += code.substring(i, i + 1);
					} else {
						spliced += dna.substring(i, i + 1);
					}
				} else {
					spliced += genMutationS();
				}
			}
		}}catch(StringIndexOutOfBoundsException e){
			return new Dna(spliced.trim(), group_num);

		}
		return new Dna(spliced.trim(), group_num);
	}
	
	//random dna string as mutation; used to replace groups
	public String genMutation() {
		return genDna(group_num);
	}
	//random letter for mutation; used to replace letters
	public String genMutationS() {
		return genDna(1);
	}

	public static String[] letters = { "T", "C", "G", "A" };
	public static int[] values = { 20, 3, 7, 1 };
	public static Random r;
	//generate random dna string
	public static String genDna(int length) {
		r = new Random();
		String code = " ";
		for (int i = 0; i < length; i++) {
			code += letters[r.nextInt(letters.length)];
		}
		code = code.trim();
		return code;

	}

	public int dna_code[];
	//generate integer values per group
	public void genInts(int group_num) {
		dna_code = new int[code.length() / group_num];
		int temp = 0;
		for (int i = 0; i < code.length(); i += group_num) {
			try {
				String c = code.substring(i, i + group_num);
				for (int l = 0; l < group_num; l++) {
					String y = c.substring(l, l + 1);
					if (y.equals(letters[0])) {
						temp += values[0];
					} else if (y.equals(letters[1])) {
						temp += values[1];
					} else if (y.equals(letters[2])) {
						temp += values[2];
					} else if (y.equals(letters[3])) {
						temp += values[3];
					}
				}
				dna_code[i / group_num] = temp;
				temp = 0;
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			} catch (StringIndexOutOfBoundsException e) {
				break;
			}
		}

	}

	public int ind_code[];
	//used for generating values of dna for visual representation; stores in ind_code
	public void genIndInts() {
		ind_code = new int[code.length()];
		int temp = 0;
		for (int i = 0; i < code.length(); i += 1) {
			try {
				String c = code.substring(i, i + 1);
				if (c.equals(letters[0])) {
					temp += values[0];
				} else if (c.equals(letters[1])) {
					temp += values[1];
				} else if (c.equals(letters[2])) {
					temp += values[2];
				} else if (c.equals(letters[3])) {
					temp += values[3];
				}

				ind_code[i] = temp;
				temp = 0;
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			} catch (StringIndexOutOfBoundsException e) {
				break;
			}
		}

	}

}
