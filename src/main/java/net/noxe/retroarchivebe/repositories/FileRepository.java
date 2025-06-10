package net.noxe.retroarchivebe.repositories;

import net.noxe.retroarchivebe.entities.ArchiveFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<ArchiveFile, Long> {

    Optional<ArchiveFile> findArchiveFileByFilenameIgnoreCase(String filename);

}
