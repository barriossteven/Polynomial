package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		Polynomial finalPoly = new Polynomial();
		finalPoly.poly = null;
		Node p1 = this.poly;
		Node p2 = p.poly;
		float coe = 0;
		int degr = 0;
		
		while ((p1 != null) && (p2!= null)){
			
			if(p1.term.degree == p2.term.degree){
				
				degr = p1.term.degree;
				coe = p1.term.coeff + p2.term.coeff;
				if(coe!=0){
					Node tmp = new Node(coe, degr, null);
					finalPoly.addToEnd(tmp);
					//System.out.println("current finalPoly: " + finalPoly);
				}
				p1 = p1.next;
				p2 = p2.next;
				
			}else if(p1.term.degree < p2.term.degree){
				coe = p1.term.coeff;
				degr = p1.term.degree;
				
				if(coe!=0){
					Node tmp = new Node(coe, degr, null);
					finalPoly.addToEnd(tmp);
					//System.out.println("current finalPoly: " + finalPoly);
				}
				
				p1 = p1.next;
			}else if(p2.term.degree < p1.term.degree){
				coe = p2.term.coeff;
				degr = p2.term.degree;
				
				if(coe!=0){
					Node tmp = new Node(coe, degr, null);
					finalPoly.addToEnd(tmp);
					//System.out.println("current finalPoly: " + finalPoly);
				}
				
				p2 = p2.next;
			}

			
			/*System.out.println("pass " + count + " coe " + coe + "degr " + degr  );
			System.out.println(finalPoly);
			count++;
			//addToFront(coe,degr,finalPoly.poly);*/
	
	
		
		}
		while ((p1 == null)||(p2== null)){
			if ((p1 == null) && (p2 == null)){
				break;
			}else if((p1 == null)){
				finalPoly.addToEnd(p2);
			}else {
				finalPoly.addToEnd(p1);
			}
			break;
		}
		
		
		
		return finalPoly;
	}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		Polynomial finalPoly = new Polynomial();
		finalPoly.poly = null;
		Polynomial tmpPoly = new Polynomial();
		tmpPoly.poly = null;
		Polynomial simPoly = new Polynomial();
		simPoly.poly= null;
		
		//System.out.println("starting tmpPoly is currently: " + tmpPoly);
		//System.out.println("starting finalPoly is currently: " + finalPoly);
		
		Node p1 = this.poly;
		Node p2 = p.poly;
		float coe = 0;
		int degr = 0;
		Node ptr = p2;
		
		if((p1 == null) || (p2 == null)){
			return finalPoly;// multiplication by 0
		}
		
		while(p1 != null){
			while (ptr != null){
				coe = p1.term.coeff * ptr.term.coeff;
				degr = p1.term.degree + ptr.term.degree;
				Node tmp = new Node(coe, degr, null);
				tmpPoly.addToEnd(tmp);
				ptr=ptr.next;
			}
			ptr = p2;
			p1 = p1.next;
			
			
		}
		//System.out.println("this is the poly: " + tmpPoly.poly);
		//System.out.println("this is tmpPoly: " + tmpPoly);
		
		finalPoly.poly = tmpPoly.Simplify(tmpPoly.poly);
		
		return finalPoly;
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		Node p1 = this.poly;
		float sum = 0;
		float lastDeg = 1;
		float valueX = x;
		//handle deg 0 and 1
		if(p1.term.degree == 0){
			sum += p1.term.coeff;
			p1 = p1.next;
		}
		if(p1.term.degree == 1){
			sum += valueX * p1.term.coeff;
			p1 = p1.next;
		}
		//handle other degs
		while(p1!=null){
			if(p1.term.degree>lastDeg){
				for(int i=0;i<p1.term.degree - lastDeg;i++){
				
					valueX = valueX*x;
				}
				sum +=(p1.term.coeff*valueX);
				lastDeg = p1.term.degree;
			}
			p1=p1.next;
			
		}
		return sum;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
	
	private void addToEnd(Node n){
		if(poly == null){
			poly = n;
		}else {
			Node ptr = poly;
			while (ptr.next != null){
				ptr = ptr.next;
			}
			ptr.next = n;
		}
	}
	
	private Node Simplify(Node n){
		Node p1 = new Node (n.term.coeff, n.term.degree, null);
		n = n.next;
		Node p2 = n;
		Node ptr = p1;
		int terminate = 0;

		
		while (ptr!= null){
			if (p2.term.degree == ptr.term.degree){
				ptr.term.coeff += p2.term.coeff;
				p2 = p2.next;
			}else
			if(p2.term.degree > ptr.term.degree){
				Node tmp = new Node (p2.term.coeff, p2.term.degree, null);
				ptr.next = tmp;
				ptr = tmp;
				p2 = p2.next;
			}else
			if(p2.term.degree< ptr.term.degree){
				Node tmpPTR = p1;
				Node prev =null;
				while(terminate == 0){
					if( p2.term.degree > tmpPTR.term.degree){
						prev = tmpPTR;
						tmpPTR = tmpPTR.next;	
					}else if( p2.term.degree == tmpPTR.term.degree){
						tmpPTR.term.coeff +=  p2.term.coeff;
						terminate = 1;
						break;
					}else if(p2.term.degree < tmpPTR.term.degree){
						Node tmpNode = new Node (p2.term.coeff, p2.term.degree, null);
						tmpNode.next = tmpPTR;
						prev.next = tmpNode;
						terminate = 1;
						break;
					}
				}
				
				terminate = 0;
				p2 = p2.next;
			}
			if(p2 == null){
				break;
			}
		}
		
		
		
		return p1;
	}
	
}
