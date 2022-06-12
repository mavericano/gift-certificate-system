package com.epam.esm.core.repository.impl;

import org.springframework.stereotype.Repository;

@Repository
public class GiftCertificateRepositoryImpl
//        implements GiftCertificateRepository
{

//    private final JdbcTemplate jdbcTemplate;
//
//    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public List<GiftCertificate> getAllGiftCertificatesByRequirements(SearchParamsDto searchParamsDto) {
//        boolean hasTag = searchParamsDto.getTagName() != null;
//        boolean hasName = searchParamsDto.getName() != null;
//        boolean hasDescription = searchParamsDto.getDescription() != null;
//        boolean hasSort = searchParamsDto.getSortBy() != null;
//        boolean toSearch = hasTag || hasName || hasDescription || hasSort;
//
//        return jdbcTemplate.query((con) -> {
//            String sql = "SELECT DISTINCT gc.* FROM gift_certificate gc JOIN gift_certificate__tag gct ON gc.id = gct.certificate_id JOIN tag t ON gct.tag_id = t.id";
//            if (toSearch) {
//                StringJoiner sj = new StringJoiner(" AND " );
//                sql += " WHERE ";
//                if (hasTag) {
//                    sj.add("t.name = ?");
//                }
//                if (hasName) {
//                    sj.add("gc.name LIKE ?");
//                }
//                if (hasDescription) {
//                    sj.add("gc.description LIKE ?");
//                }
//                sql += sj.toString();
//                if (hasSort) {
//                    sql += "ORDER BY gc." + searchParamsDto.getSortBy() + " " + searchParamsDto.getSortType().toUpperCase();
//                }
//            }
//
//            PreparedStatement ps = con.prepareStatement(sql);
//            int index = 1;
//            if (hasTag) {
//                ps.setString(index++, searchParamsDto.getTagName());
//            }
//            if (hasName) {
//                ps.setString(index++, "%" + searchParamsDto.getName() + "%");
//            }
//            if (hasDescription) {
//                ps.setString(index, "%" + searchParamsDto.getDescription() + "%");
//            }
//            return ps;
//                }, new GiftCertificateRowMapper());
//    }
//
//    //if no tags for a certificate, produces empty set
//    @Override
//    public Set<Tag> getAllTagsForGiftCertificateById(long id) {
//        return jdbcTemplate.queryForStream(
//                "SELECT tag.id, tag.name FROM gift_certificate__tag JOIN tag ON tag_id = tag.id WHERE certificate_id = ?",
//                new TagRowMapper(), id).collect(Collectors.toSet());
//    }
//
//    @Override
//    public Optional<GiftCertificate> getGiftCertificateById(long id) {
//        try {
//            return Optional.ofNullable(
//                    jdbcTemplate.queryForObject("SELECT * FROM gift_certificate WHERE id = ?", new GiftCertificateRowMapper(), id));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    //if db is empty, produces empty list
//    @Override
//    public List<GiftCertificate> getAllGiftCertificates() {
//        return jdbcTemplate.query("SELECT * FROM gift_certificate", new GiftCertificateRowMapper());
//    }
//
//    @Override
//    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        LocalDateTime now = LocalDateTime.now();
//        jdbcTemplate.update(con -> {
//            PreparedStatement ps =
//                    con.prepareStatement(
//                            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)",
//                            Statement.RETURN_GENERATED_KEYS);
//            fillPreparedStatement(ps, giftCertificate);
//            ps.setObject(5, Timestamp.valueOf(now));
//            ps.setObject(6, Timestamp.valueOf(now));
//            return ps;
//        }, keyHolder);
//        if (keyHolder.getKey() != null) {
//            giftCertificate.setId(keyHolder.getKey().longValue());
//        } else {
//            throw new KeyHolderException();
//        }
//        giftCertificate.setCreateDate(now);
//        giftCertificate.setLastUpdateDate(now);
//        return giftCertificate;
//    }
//
//    @Override
//    public void linkTagsToGiftCertificate(long giftCertificateId, Set<Tag> tagSet) {
//        tagSet.stream().map(Tag::getId).forEach(id ->
//                jdbcTemplate.update("INSERT INTO gift_certificate__tag VALUES (?,?)", giftCertificateId, id));
//    }
//
//    @Override
//    public void unlinkTagsFromGiftCertificate(long giftCertificateId, Set<Tag> tagSet) {
//        tagSet.stream().map(Tag::getId).forEach(id ->
//                jdbcTemplate.update("DELETE FROM gift_certificate__tag WHERE certificate_id = ? AND tag_id = ?", giftCertificateId, id));
//    }
//
//    @Override
//    public void removeGiftCertificateById(long id) {
//        unlinkTagsFromGiftCertificate(id, getAllTagsForGiftCertificateById(id));
//        jdbcTemplate.update("DELETE FROM gift_certificate WHERE id = ?", id);
//    }
//
//    @Override
//    public GiftCertificate updateGiftCertificateFull(GiftCertificate giftCertificate) {
//        LocalDateTime now = LocalDateTime.now();
//        jdbcTemplate.update((con) -> {
//            String sql = "UPDATE gift_certificate SET ";
//            sql += buildSetExpression();
//            sql += " WHERE id = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            fillPreparedStatement(ps, giftCertificate);
//            ps.setObject(5, Timestamp.valueOf(now));
//            ps.setLong(6, giftCertificate.getId());
//            return ps;
//        });
//
//        giftCertificate.setLastUpdateDate(now);
//        giftCertificate.setCreateDate(getCreateTimeForCertificateById(giftCertificate.getId()));
//        return giftCertificate;
//    }
//
//    private LocalDateTime getCreateTimeForCertificateById(long id) {
//        return jdbcTemplate.query(con -> {
//            PreparedStatement ps = con.prepareStatement("SELECT create_date FROM gift_certificate WHERE id = ?");
//            ps.setLong(1, id);
//            return ps;
//        }, rs -> {
//            rs.next();
//            return rs.getTimestamp(1).toLocalDateTime();
//        });
//    }
//
//    private void fillPreparedStatement(PreparedStatement ps, GiftCertificate giftCertificate) throws SQLException {
//        ps.setString(1, giftCertificate.getName());
//        ps.setString(2, giftCertificate.getDescription());
//        ps.setBigDecimal(3, giftCertificate.getPrice());
//        ps.setInt(4, giftCertificate.getDuration());
////        ps.setObject(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
////        ps.setObject(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
//    }
//
//    private String buildSetExpression() {
//        StringJoiner sj = new StringJoiner(", ");
//        sj.add("name= ?");
//        sj.add("description= ?");
//        sj.add("price= ?");
//        sj.add("duration= ?");
////        sj.add("create_date= ?");
//        sj.add("last_update_date= ?");
//
//        return sj.toString();
//    }
//
//    @Override
//    public boolean existsGiftCertificateById(long id) {
//        return jdbcTemplate.query("SELECT count(*) FROM gift_certificate WHERE id = ?", rs -> {
//            rs.next();
//            return rs.getInt(1);
//        }, id) > 0;
//    }
}
