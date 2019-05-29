package ro.example.lab.offers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OfferDetailsRepository {

    private List<Offer> mOffers;

    public OfferDetailsRepository() {
        mOffers = new ArrayList<>();
        mockOffers();
    }

    private void mockOffers() {
        Offer offer = new Offer("Oferta la cafea", Constants.LOREM_IPSUM,
                                "https://img.etimg.com/thumb/msid-67055775,width-643,imgsize-709079,resizemode-4/coffeebeans.jpg",
                                "1 punct = 1 cafea", 5, 0,
                                new GregorianCalendar(2019, 5, 10),
                                new GregorianCalendar(2019, 5, 20));

        mOffers.add(offer);

        offer = new Offer("Oferta la carburant", Constants.LOREM_IPSUM,
                          "https://www.golegal.co.za/wp-content/uploads/2018/12/fuel-price.jpg",
                          "1 punct = 5 litri de carburant", 5, 0,
                          new GregorianCalendar(2019, 5, 10),
                          new GregorianCalendar(2019, 6, 10));
        mOffers.add(offer);

        offer = new Offer("Invita-ti prietenii", Constants.LOREM_IPSUM,
                          "https://d39l2hkdp2esp1.cloudfront.net/img/photo/139946/139946_00_2x.jpg?20180207093330",
                          "1 punct = 1 prieten invitat", 3, 0,
                          new GregorianCalendar(2019, 5, 10),
                          new GregorianCalendar(2020, 5, 10));
        mOffers.add(offer);

    }

    public LiveData<Offer> getDetails(int id) {
        final MutableLiveData<Offer> data = new MutableLiveData<>();
        data.setValue(mOffers.get(id));

        return data;
    }
}
