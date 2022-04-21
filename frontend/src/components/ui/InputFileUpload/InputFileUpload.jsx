import { useState, useEffect } from "react"
import { generateRandomId } from "src/utils"
import { BaseButton } from "src/components"
import styles from "./InputFileUpload.module.scss"

export const InputFileUpload = ({id, fileType, onUploadSuccess = () => {}, onUploadFail = () => {}}, ...otherProps) => {
  const inputId = id ?? generateRandomId()

  const [file, setFile] = useState()

  useEffect(() => {
    let error = false
    if (file) {
      if (!file.type.includes(fileType)) {
        setFile(null)
        onUploadFail("Arquivo com formato inválido selecionado.")
        error = true
      }

      if (file.size > 1024 * 1024) {
        setFile(null)
        onUploadFail("Tamanho máximo de arquivo é 1 MB.")
        error = true
      }
    }
    
    if(!error) {
      onUploadSuccess(file)
    }
    // eslint-disable-next-line
  }, [file])

  return <>
    <div className={styles.FileUpload}>
      <input type="file" id={inputId} onChange={(e => setFile(e.currentTarget.files[0]))} {...otherProps} />
      <label htmlFor={inputId}>
        <BaseButton>escolher arquivos</BaseButton>
        <span>{ file ? file.name : "Nome do arquivo..." }</span>
      </label>
    </div>
  </>
}