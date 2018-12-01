package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import com.danit.repositories.specifications.ClientListSpecification;
import com.danit.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ClientServiceImpl extends AbstractEntityService<Client, ClientListRequestDto> {

}
