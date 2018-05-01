package edu.westga.cs1302.wordCloud.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;;


/**
 * The Class WordManager.
 * @author Junbin Kwon
 * @version 2018-05-01
 */
public class WordManager implements  Collection<WordData> {
	
	/** The word manage. */
	private Map<String, WordData> wordManage;
	
	/** The frequencies. */
	private ArrayList<Integer> frequencies;
	
	/**
	 * Instantiates a new word manager.
	 */
	public WordManager() {
		this.frequencies = new ArrayList<Integer>();
		this.wordManage = new HashMap<String, WordData>();
	}
	
	/**
	 * Gets the frequency by word.
	 *
	 * @param word the word
	 * @return the frequency by word
	 */
	public WordData getFrequencyByWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null...");
		}
		return this.wordManage.get(word);
	}
	
	/**
	 * Contains word.
	 *
	 * @param word the word
	 * @return true, if successful
	 */
	public boolean containsWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null..");
		}
		return this.wordManage.containsKey(word);
	}
	
	/**
	 * Removes the by word.
	 *
	 * @param word the word
	 * @return true, if successful
	 */
	public boolean removeByWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null....");
		}
		WordData removeWord = this.wordManage.remove(word);
		
		return (removeWord != null);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(WordData data) {
		if (data == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		this.wordManage.put(data.getData(), data);
		return this.wordManage.containsKey(data.getData());	
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends WordData> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null.....");
		}
		for (WordData word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null......");
			}
			this.wordManage.put(word.getData(), word);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		this.wordManage.clear();		
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null.......");
		}
		WordData theData = (WordData) word;
		return this.wordManage.containsKey(theData.getData());
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null.......");
		}
		for (Object word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null.........");
			}
			if (!this.contains(word)) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.wordManage.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<WordData> iterator() {
		return this.wordManage.values().iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null........");
		}
		WordData data = (WordData) word;
		WordData removeData = this.wordManage.remove(data.getData());
		return (data == removeData);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
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
	
	/**
	 * Gets the word manage.
	 *
	 * @return the word manage
	 */
	public Map<String, WordData> getWordManage() {
		return this.wordManage;
	}

	/**
	 * Sets the word manage.
	 *
	 * @param wordManage the word manage
	 */
	public void setWordManage(Map<String, WordData> wordManage) {
		this.wordManage = wordManage;
	}
	
	/**
	 * Sort by frequency.
	 *
	 * @return the map
	 */
	public Map<String, WordData> sortByFrequency() {
		this.frequencies = new ArrayList<Integer>();
		Set<Entry<String, WordData>> set = this.wordManage.entrySet();
		List<Entry<String, WordData>> list = new ArrayList<Entry<String, WordData>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return -1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return 1;
				}
				return 0;
			}
		});
		this.wordManage = new LinkedHashMap<String, WordData>();
		for (Map.Entry<String, WordData> entry : list) {
			this.wordManage.put(entry.getKey(), entry.getValue());
			this.frequencies.add(entry.getValue().getFrequency());
		}
		return this.wordManage;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return this.wordManage.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		return this.wordManage.values().toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] word) {
		if (word == null) {
			throw new IllegalArgumentException("array should not be null");
		}
		return this.wordManage.values().toArray(word);
	}

	/**
	 * Sort default.
	 *
	 * @return the map
	 */
	public Map<String, WordData>  sortDefault() {
		Set<Entry<String, WordData>> set = this.wordManage.entrySet();
		List<Entry<String, WordData>> list = new ArrayList<Entry<String, WordData>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return -1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return 1;
				}
				return 0;
			}
		});
		this.wordManage = new HashMap<String, WordData>();
		for (Map.Entry<String, WordData> entry : list) {
			this.wordManage.put(entry.getKey(), entry.getValue());
		}
		return this.wordManage;	
	}

	/**
	 * Sort mixed.
	 *
	 * @return the map
	 */
	public Map<String, WordData> sortMixed() {
		this.frequencies = new ArrayList<Integer>();
		Set<Entry<String, WordData>> set = this.wordManage.entrySet();
		List<Entry<String, WordData>> list = new ArrayList<Entry<String, WordData>>(set);
		List<Entry<String, WordData>> listRe = new ArrayList<Entry<String, WordData>>(set);
		this.makeCollectionsForCompare(list, listRe);
		int size = this.wordManage.size();
		this.wordManage = new LinkedHashMap<String, WordData>();
		int count = 0;
		int countRe = 0;
		for (int index = 0; index <= size + 2; index++) {
			if (index % 2 == 0) {
				this.wordManage.put(list.get(count).getKey(), list.get(count).getValue());
				count++;
			} else if (index % 2 == 1) {
				this.wordManage.put(listRe.get(countRe).getKey(), listRe.get(countRe).getValue());
				countRe++;
			}
		}
		return this.wordManage;
		
	}

	private void makeCollectionsForCompare(List<Entry<String, WordData>> list, List<Entry<String, WordData>> listRe) {
		Collections.sort(list, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return -1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return 1;
				}
				return 0;
			}
		});
		Collections.sort(listRe, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return 1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return -1;
				}
				return 0;
			}
		});
	}
}
