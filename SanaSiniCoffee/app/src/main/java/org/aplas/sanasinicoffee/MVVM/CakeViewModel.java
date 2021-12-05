package org.aplas.sanasinicoffee.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.aplas.sanasinicoffee.Model.CakeModel;

import java.util.List;

public class CakeViewModel extends ViewModel implements Repository.cakeList {

    MutableLiveData<List<CakeModel>> mutableLiveData = new MutableLiveData<List<CakeModel>>();
    Repository repository = new Repository(this);

    public CakeViewModel() {
        repository.getCake();
    }

    public LiveData<List<CakeModel>> getCakeList(){
        return  mutableLiveData;
    }

    @Override
    public void cakeList(List<CakeModel> cakeModels) {
        mutableLiveData.setValue(cakeModels);
    }
}
