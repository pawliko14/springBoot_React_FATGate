package sqlObjects;

public class WorkersAndID {

	private String WorkerName;
	private int WorkerID;
	
	public WorkersAndID(String workerName, int workerID) {
		super();
		WorkerName = workerName;
		WorkerID = workerID;
	}
	public String getWorkerName() {
		return WorkerName;
	}
	public void setWorkerName(String workerName) {
		WorkerName = workerName;
	}
	public int getWorkerID() {
		return WorkerID;
	}
	public void setWorkerID(int workerID) {
		WorkerID = workerID;
	}
	@Override
	public String toString() {
		return "WorkersAndID [WorkerName=" + WorkerName + ", WorkerID=" + WorkerID + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + WorkerID;
		result = prime * result + ((WorkerName == null) ? 0 : WorkerName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkersAndID other = (WorkersAndID) obj;
		if (WorkerID != other.WorkerID)
			return false;
		if (WorkerName == null) {
			if (other.WorkerName != null)
				return false;
		} else if (!WorkerName.equals(other.WorkerName))
			return false;
		return true;
	}
	
	
	
}
