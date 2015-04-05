package umjdt.concepts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.TransactionManager;

import umjdt.util.Participant;


public class ResourceManager implements Participant, Serializable
{

	private String name;
	private int rmId;
	private Resource resource;
	private TransactionManager manager;
	private List<Resource> listResource = new ArrayList<>();
	//private LockManager lockEvent;
	
	
	public ResourceManager(){
		
	}
	public ResourceManager(String _name, int _rmId){
		this.name=_name;
		this.rmId=_rmId;
	}
	
	public int registerResource(Resource _resource){
		listResource.add(_resource);
		return listResource.size();
	}
	public int deregisterResource(Resource resource){
		listResource.remove(resource);
		return listResource.size();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRmId() {
		return rmId;
	}
	public void setRmId(int rmId) {
		this.rmId = rmId;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public TransactionManager getTm() {
		return manager;
	}
	public void setTm(TransactionManager tm) {
		this.manager = tm;
	}
	public List<Resource> getListResource() {
		return listResource;
	}
	public void setListResource(List<Resource> listResource) {
		this.listResource = listResource;
	}

	@Override
	public String participantName() {
		// TODO Auto-generated method stub
		return "RM";
	}
}
