
public class LinkStrand implements IDnaStrand {
    private int myAppends;
    
    /**
     * Create a strand representing an empty DNA strand, length of zero.
     */
    public LinkStrand() {
    	// Syntactic trick: calls the other constructor (the one that
    	// takes a String) with an empty String.
    	this("");
    }

    /**
     * Create a strand representing s. No error checking is done to 
     * see if s represents valid genomic/DNA data.
     * @param s is the source of cgat data for this strand
     */
    public LinkStrand(String s) {
    	myFirst=new Node(s);
		myLast=myFirst;
		mySize=s.length();
		
    }
    
	@Override
	
	public IDnaStrand cutAndSplice(String enzyme, String splicee) {
		// TODO Auto-generated method stub
	     int pos = 0;
	     int start = 0;
	     StringBuilder search = new StringBuilder(myFirst.info);
	     boolean first = true;
	     LinkStrand ret = null;
        
        /*
         * The next line is very syntax-dense. .indexOf looks for the 
         * first index at which enzyme occurs, starting at pos. Saying 
         * pos = ... assigns the result of that operation to the pos
         * variable; the value of pos is then compared against zero.
         * 
         * .indexOf returns -1 if enzyme can't be found. Therefore, this
         * line is:
         * 
         * "While I can find enzyme, assign the location where it occurs
         * to pos, and then execute the body of the loop."
         */
	     while ((pos = search.indexOf(enzyme, pos)) >= 0) {
	            if (first){
	                ret = new LinkStrand(search.substring(start, pos));
	                first = false;
	            }
	            else {
	                 ret.append(search.substring(start, pos));
	                 
	            }
	            start = pos + enzyme.length();
	            ret.append(splicee);
	            pos++;
	        }

	        if (start < search.length()) {
	        	// NOTE: This is an important special case! If the enzyme
	        	// is never found, return an empty String.
	        	if (ret == null){
	        		ret = new LinkStrand("");
	        	}
	        	else {
	        		ret.append(search.substring(start));
	        	}
	        }
	        return ret;
	    }
		
		
	

	@Override
	
	public long size() {
		// TODO Auto-generated method stub
		return mySize;
	}

	@Override
	public void initializeFrom(String source) {
		// TODO Auto-generated method stub
		myFirst=new Node(source);
		myLast=myFirst;
		mySize=source.length();
		
	}

	@Override
	public String strandInfo() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	@Override
	public IDnaStrand append(IDnaStrand dna) {
		// TODO Auto-generated method stub
		if (dna instanceof LinkStrand){
			LinkStrand x = (LinkStrand) dna;
			Node current=x.myFirst;
			myLast.next=current; 
			myLast=x.myLast;
			mySize+=dna.size();
		current=x.myFirst;
		while(current!=null){
			myAppends++;
			current=current.next;
		}	
		}
		else{
            return append(dna.toString());
		}
		return null;
	}

	
	
	@Override
	public IDnaStrand append(String dna) {
		// TODO Auto-generated method stub
		if (myFirst==null){
			initializeFrom(dna);
			myAppends++;
			mySize+=dna.length();
			return this;
		}
		else{
			myLast.next=new Node(dna);
			myLast=myLast.next;
			myAppends++;
			mySize+=dna.length();
			return this;
		}
	}

	@Override
	public IDnaStrand reverse() {
		// TODO Auto-generated method stub
		myLast=myFirst;
		if (myLast==null){
			return this;
		}
		Node current=myFirst;
		Node current2=myFirst.next;
		Node current3=null;
		if (current.next==null){
		StringBuilder info=new StringBuilder(current.info);
		info.reverse();
		current.info=info.toString();
		return this;
		}
		while (current.next!=null){
			StringBuilder info=new StringBuilder(current.info);
			info.reverse();
			current.info=info.toString();
			current.next=current3;
			current3=current;
			current=current2;
			current2=current.next;
		
		}	
		myFirst=current;

		return this;
	}

	@Override
	public String getStats() {
		// TODO Auto-generated method stub
		return String.format("# append calls = %d",myAppends);
	}



	public class Node{
		String info;
		Node next;
		Node (String s){
		info=s;
		next=null;
		}
		Node (Node s){
			info=s.info;
			next=s.next;
		}
		Node (Node a, Node b){
			info=a.info;
			next=b;
		}
	}
		
		private Node myFirst, myLast;
		private long mySize;
		
	


		public void addToHead(Node s){
			myFirst=new Node(s, myFirst);
		      
		    }
		    
		
		
			
					    

		}

	
		
