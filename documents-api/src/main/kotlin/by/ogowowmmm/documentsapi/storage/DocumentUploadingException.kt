package by.ogowowmmm.documentsapi.storage

class DocumentUploadingException(reason: Exception) :
    Exception("Document uploading failed", reason)