package com.ffa.back.services;

import com.ffa.back.dto.GroupCreateDTO;
import com.ffa.back.dto.GroupDTO;
import com.ffa.back.dto.GroupUpdateDTO;
import com.ffa.back.mappers.GroupMapper;
import com.ffa.back.models.Group;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.UserRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {


    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserRepository userRepository,
                            GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    @Transactional
    public GroupDTO createGroup(GroupCreateDTO groupCreateDTO) {
        // Validar y obtener el propietario
        User owner = userRepository.findById(groupCreateDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Propietario no encontrado con id: " + groupCreateDTO.getOwnerId()));

        // Validar y obtener los miembros
        List<User> members = null;
        if (groupCreateDTO.getMemberIds() != null && !groupCreateDTO.getMemberIds().isEmpty()) {
            members = userRepository.findAllById(groupCreateDTO.getMemberIds());

            // Validar que todos los miembros existan
            if (members.size() != groupCreateDTO.getMemberIds().size()) {
                throw new ResourceNotFoundException("Uno o más miembros no existen.");
            }
        }

        Group group = new Group();
        // Mapear DTO a entidad
        group = groupMapper.groupCreateDTOToGroup(groupCreateDTO, group);
        group.setOwner(owner);
        group.setMembers(members);

        // Guardar grupo
        Group savedGroup = groupRepository.save(group);

        // Mapear entidad a DTO y retornar
        return groupMapper.toGroupDTO(savedGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toGroupDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GroupDTO> getGroupById(Long id) {
        return groupRepository.findById(id)
                .map(groupMapper::toGroupDTO);
    }

    @Override
    @Transactional
    public Optional<GroupDTO> updateGroup(Long id, GroupUpdateDTO groupUpdateDTO) {
        Optional<Group> groupOpt = groupRepository.findById(id);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();

            // Mapear los cambios del DTO a la entidad existente
            groupMapper.updateGroupFromDTO(groupUpdateDTO, group);

            // Validar y actualizar el propietario si se proporciona
            if (groupUpdateDTO.getOwnerId() != null) {
                User owner = userRepository.findById(groupUpdateDTO.getOwnerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Propietario no encontrado con id: " + groupUpdateDTO.getOwnerId()));
                group.setOwner(owner);
            }

            // Validar y actualizar los miembros si se proporcionan
            if (groupUpdateDTO.getMemberIds() != null) {
                List<User> members = userRepository.findAllById(groupUpdateDTO.getMemberIds());

                // Validar que todos los miembros existan
                if (members.size() != groupUpdateDTO.getMemberIds().size()) {
                    throw new ResourceNotFoundException("Uno o más miembros no existen.");
                }

                group.setMembers(members);
            }

            // Guardar cambios
            Group updatedGroup = groupRepository.save(group);

            // Mapear a DTO y retornar
            return Optional.of(groupMapper.toGroupDTO(updatedGroup));
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean deleteGroup(Long id) {
        Optional<Group> groupOpt = groupRepository.findById(id);
        if (groupOpt.isPresent()) {
            groupRepository.delete(groupOpt.get());
            return true;
        } else {
            return false;
        }
    }
}