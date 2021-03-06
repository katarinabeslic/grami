package com.njt.projekat.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.njt.projekat.entity.Artist;
import org.springframework.data.jpa.domain.Specification;

import com.njt.projekat.entity.Format;
import com.njt.projekat.entity.Genre;
import com.njt.projekat.entity.Vinyl;

public class VinylSpecification {

	public VinylSpecification() {

	}

	public static Specification<Vinyl> filterBy(String format, List<String> genres, String search) {
		return (Specification<Vinyl>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			query.distinct(true);
			if (format != null) {
				Join<Vinyl, Format> joinFormat = root.join("format");
				predicates.add(criteriaBuilder.and(joinFormat.get("name").in(format)));
			}
			if (genres != null) {
				Join<Vinyl, Genre> joinGenre = root.join("genres");
				predicates.add(criteriaBuilder.and(joinGenre.get("name").in(genres)));
			}
			if (search != null && !search.isEmpty()) {
				Join<Vinyl, Artist> joinArtist = root.join("artist");
				Predicate vinylName = criteriaBuilder.like(root.get("vinylName"), "%" + search + "%");
				Predicate artistName = criteriaBuilder.like(joinArtist.get("stageName"), "%" +search + "%");
				Predicate searchPredicate = criteriaBuilder.or(vinylName, artistName);
				predicates.add(searchPredicate);
				//predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("vinylName"), "%" + search + "%")));
				//predicates.add(criteriaBuilder.and(criteriaBuilder.like(joinArtist.get("stageName"), "%" +search + "%")));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
