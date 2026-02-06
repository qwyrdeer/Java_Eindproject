//package nl.novi.GalacticEndgame.mappers;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserExtendedDTOMapper extends UserMapper {
//
//    private final UserMapper userMapper;
//
//    public AlbumExtendedDTOMapper(ProfileMapper profileMapper, UserMapper userMapper) {
//        super(profileMapper, userMapper);
//        this.userMapper= userMapper;
//    }
//
//    @Override
//    public AlbumExtendedResponseDTO mapToDto(AlbumEntity model) {
//        AlbumExtendedResponseDTO result = (AlbumExtendedResponseDTO) super.mapToDto(model);
//        result.setStock(stockDTOMapper.mapToDto(model.getStockItems()));
//        return result;
//    }
//}