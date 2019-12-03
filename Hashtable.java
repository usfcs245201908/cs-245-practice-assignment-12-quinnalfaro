import java.util.ArrayList;

public class Hashtable{
	private ArrayList<HashNode> bucket;
	private final double LOAD_THRESHOLD = 0.5;
	private int entries;
	private int size;

	public Hashtable() {
		bucket = new ArrayList<>();
		entries = 10;
		size = 0;
		for(int i = 0; i < entries; i++){
			bucket.add(null);
		}
	}

	private class HashNode {
		String key;
		String value;
		HashNode next;
		public HashNode(String key, String value){
			this.key = key;
			this.value = value;
			next = null;
		}
	}

	public String get (String key){
		HashNode head = bucket.get(getHash(key) % bucket.size());
		while(head != null){
			if(head.key.equals(key)){
				return head.value;
			} else {
				head = head.next;
			}
		}
		return null;
	}

	public boolean containsKey (String key){
		HashNode head = bucket.get(getHash(key) % bucket.size());
		while(head != null){
			if(head.key.equals(key)){
				return true;
			} else {
				head = head.next;
			}
		}
		return false;
	}

	public int getHash(String key){
		return key.hashCode();
	}


	public String remove (String key) throws Exception {
		HashNode head = bucket.get(getHash(key) % bucket.size());
		HashNode prev = null;
		if(head == null){
			throw new Exception("Key is not present in hashtable");
		}
		while(head != null) {
			if(head.key.equals(key)) {	
				size--;
				if(prev != null){
					prev.next = head.next;
				}
				else{
					bucket.set(getHash(key) % bucket.size(), head.next);
				}
				return head.value;
			}
			else {
				prev = head;
				head = head.next;
			}
		}
		return head.value;
	}


	public void put (String key, String value) {
		HashNode head = bucket.get(getHash(key) % bucket.size());
		while(head != null) {
			if(head.key.equals(key)) {
				head.value = value;
				return;
			}
			head = head.next;
		}
		size++;
		head = bucket.get(getHash(key) % bucket.size());
		HashNode node = new HashNode(key, value);
		node.next = head;
		bucket.set(getHash(key) % bucket.size(), node);
		if((1.0 * size)/entries >= LOAD_THRESHOLD){
			ArrayList<HashNode> newBucket = bucket;
			bucket = new ArrayList<>();
			entries = 2 * entries;	
			size = 0;
			for(int i = 0; i < entries; i++) {
				bucket.add(null); 
			}
			for(HashNode newNode : newBucket){
				while(newNode != null){
					put(newNode.key, newNode.value);
					newNode = newNode.next;
				}
			}
		}
	}
}



















