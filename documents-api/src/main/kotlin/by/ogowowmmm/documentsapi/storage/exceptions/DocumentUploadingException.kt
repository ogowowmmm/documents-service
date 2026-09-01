package by.ogowowmmm.documentsapi.storage.exceptions

class DocumentUploadingException(reason: Exception) :
    Exception("Document uploading failed", reason)