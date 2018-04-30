package edu.westga.cs1302.wordCloud.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class WordManager implements  Collection<WordData>{
	private Map<String, WordData> wordManage;
	
	public WordManager() {
		this.wordManage = new HashMap<String, WordData>();
	}
	
	public WordData getFrequencyByWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		return this.wordManage.get(word);
	}
	
	public boolean containsWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		return this.wordManage.containsKey(word);
	}
	
	public boolean removeByWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		WordData removeWord = this.wordManage.remove(word);
		
		return (removeWord != null);
	}
	
	@Override
	public boolean add(WordData data) {
		if (data == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		this.wordManage.put(data.getData(), data);
		return this.wordManage.containsKey(data.getData());	
	}

	@Override
	public boolean addAll(Collection<? extends WordData> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null");
		}
		for (WordData word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null");
			}
			this.wordManage.put(word.getData(), word);
		}
		return true;
	}

	@Override
	public void clear() {
		this.wordManage.clear();		
	}

	@Override
	public boolean contains(Object word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null");
		}
		WordData theData = (WordData) word;
		return this.wordManage.containsKey(theData.getData());
	}

	@Override
	public boolean containsAll(Collection<?> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null");
		}
		for (Object word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null");
			}
			if (!this.contains(word)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return this.wordManage.isEmpty();
	}

	@Override
	public Iterator<WordData> iterator() {
		return this.wordManage.values().iterator();
	}

	@Override
	public boolean remove(Object word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null");
		}
		WordData data = (WordData) word;
		WordData removeData = this.wordManage.remove(data.getData());
		return (data == removeData);
	}

	@Override
	public boolean removeAll(Collection<?> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null");
		}
		boolean removedAll = true;

		for (Object word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null");
			}

			if (!this.remove(word)) {
				removedAll = false;
			}

		}
		return removedAll;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return this.wordManage.size();
	}

	@Override
	public Object[] toArray() {
		return this.wordManage.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] word) {
		if (word == null) {
			throw new IllegalArgumentException("array should not be null");
		}
		return this.wordManage.values().toArray(word);
	}
}
