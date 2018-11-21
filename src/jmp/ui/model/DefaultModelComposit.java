package jmp.ui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jmp.ui.mvc.Model;
import jmp.ui.mvc.ModelListener;

public class DefaultModelComposit implements ModelComposit
{
	private Map<String, Model> models;
	private List<ModelListener> listenersCache;
	
	public DefaultModelComposit()
	{
		this.models = new TreeMap<String, Model>();
		this.listenersCache = new ArrayList<ModelListener>();
	}

	public void addModelListener(ModelListener l)
	{
		this.listenersCache.add(l);
		for(Model m: this.models.values())
			m.addModelListener(l);
	}

	public void removeModelListener(ModelListener l)
	{
		this.listenersCache.remove(l);
		for(Model m: this.models.values())
			m.removeModelListener(l);
	}

	public void addModel(String key, Model model)
	{
		this.models.put(key, model);
		for(ModelListener ml: this.listenersCache)
			model.addModelListener(ml);
	}

	public void removeModel(String key)
	{
		this.models.remove(key);
	}

	public Model getModel(String key)
	{
		return this.models.get(key);
	}
	
	public Collection<Model> models()
	{
		return this.models.values();
	}

	public void modelChange()
	{	
	}
}
