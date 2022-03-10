export const makeInitials = fullName => {
  if (typeof fullName !== "string") {
    return "??"
  }
  
  const arrayFromName = fullName.split(" ").map(name => name[0].toUpperCase())
  return `${arrayFromName[0]}${arrayFromName[arrayFromName.length - 1]}`
}