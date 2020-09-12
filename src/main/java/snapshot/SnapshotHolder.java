package snapshot;

import snapshot.Snapshot;

import java.util.*;
public class SnapshotHolder {
    private Stack<Snapshot> snapshots = new Stack<>();
  
    public Snapshot popSnapshot() {
      return snapshots.pop();
    }
  
    public void pushSnapshot(Snapshot snapshot) {
      snapshots.push(snapshot);
    }
  }