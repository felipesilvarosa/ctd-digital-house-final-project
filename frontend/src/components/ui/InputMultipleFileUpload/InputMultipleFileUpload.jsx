import { useState, useEffect } from "react"
import { generateRandomId } from "src/utils"
import { BaseButton } from "src/components"
import styles from "./InputMultipleFileUpload.module.scss"

export const InputMultipleFileUpload = ({id, fileType, onUploadSuccess = () => {}, onUploadFail = () => {}}, ...otherProps) => {
  const inputId = id ?? generateRandomId()

  const [files, setFiles] = useState([])

  useEffect(() => {
    let error = null
    if (files.length > 0) {
      for (let file of files) {
        if (!file.type.includes(fileType)) {
          setFiles(null)
          onUploadFail("Arquivo com formato inválido selecionado.")
          error = true
        }
      }
      
      if (!error)
        onUploadSuccess(files)
    }
    // eslint-disable-next-line
  }, [files])

  return <>
    <div className={styles.FileUpload}>
      <input type="file" multiple id={inputId} onChange={(e => setFiles(e.currentTarget.files))} {...otherProps} />
      <label htmlFor={inputId}>
        <BaseButton>escolher arquivos</BaseButton>
      </label>
    </div>
  </>
}