package com.parcel.tracker.infrastructure.mongo;

import com.parcel.tracker.application.ParcelRepository;
import com.parcel.tracker.domain.Parcel;
import com.parcel.tracker.domain.ParcelId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class ParcelRepositoryMongo implements ParcelRepository {
    private final SpringDataParcelRepository springDataParcelRepository;

    @Override
    public Iterable<Parcel> findAll() {
        var firstPage = springDataParcelRepository.findAll(Pageable.ofSize(100));

        return () -> new ParcelPageIterator(springDataParcelRepository, firstPage);
    }

    @Override
    public boolean existsById(ParcelId parcelId) {
        return springDataParcelRepository.existsById(parcelId.id());
    }

    @Override
    public void save(Parcel parcel) {
        springDataParcelRepository.save(ParcelMongo.from(parcel));
    }

    private static class ParcelPageIterator implements Iterator<Parcel> {
        private final SpringDataParcelRepository springDataParcelRepository;
        private Page<ParcelMongo> currentPage;
        private Iterator<ParcelMongo> currentPageIterator;

        ParcelPageIterator(SpringDataParcelRepository repository, Page<ParcelMongo> initialPage) {
            this.springDataParcelRepository = repository;
            this.currentPage = initialPage;
            this.currentPageIterator = initialPage.iterator();
        }

        @Override
        public boolean hasNext() {
            return currentPageIterator.hasNext() || currentPage.hasNext();
        }

        @Override
        public Parcel next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more parcels");
            }

            if (currentPageIterator.hasNext()) {
                return currentPageIterator.next().toParcel();
            }

            currentPage = springDataParcelRepository.findAll(currentPage.nextPageable());
            currentPageIterator = currentPage.iterator();

            return currentPageIterator.next().toParcel();
        }
    }
}
