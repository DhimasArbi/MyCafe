package org.aplas.sanasinicoffee.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.aplas.sanasinicoffee.Model.CoffeeModel;

import java.util.List;

public class CoffeeViewModel extends ViewModel implements Repository.coffeeList {
    MutableLiveData<List<CoffeeModel>> mutableLiveData = new MutableLiveData<List<CoffeeModel>>();
    Repository repository = new Repository(this);

    public CoffeeViewModel() {
        repository.getCoffee();
    }

    public LiveData<List<CoffeeModel>> getCofeeList(){
        return mutableLiveData;
    }

    @Override
    public void coffeeList(List<CoffeeModel> coffeeModels) {
        mutableLiveData.setValue(coffeeModels);
    }
}
